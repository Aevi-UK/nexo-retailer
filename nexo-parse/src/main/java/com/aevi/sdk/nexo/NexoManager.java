package com.aevi.sdk.nexo;

import android.support.annotation.NonNull;

import com.aevi.sdk.nexo.extramodel.LoginNotRequired;
import com.aevi.sdk.nexo.extramodel.RejectedRequest;
import com.aevi.sdk.nexo.extramodel.requests.Login;
import com.aevi.sdk.nexo.extramodel.requests.Logout;
import com.aevi.sdk.nexo.extramodel.responses.LoggedIn;
import com.aevi.sdk.nexo.extramodel.responses.LoggedOut;
import com.aevi.sdk.nexo.extramodel.responses.LoginFailure;
import com.aevi.sdk.nexo.extramodel.responses.LogoutFailure;
import com.aevi.sdk.nexo.manager.NexoState;
import com.aevi.sdk.nexo.model.ErrorCondition;
import com.aevi.sdk.nexo.model.LoginRequest;
import com.aevi.sdk.nexo.model.MessageFormat;
import com.aevi.sdk.nexo.model.NexoDeserialiser;
import com.aevi.sdk.nexo.model.POISoftware;
import com.aevi.sdk.nexo.model.POIStatus;
import com.aevi.sdk.nexo.model.POISystemData;
import com.aevi.sdk.nexo.model.PrinterStatus;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;
import com.aevi.sdk.nexo.translators.NexoFlow;

import java.time.ZonedDateTime;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Entry point for managing communications over Nexo.
 */
public class NexoManager {
    private final PublishSubject<String> xmlEmitter = PublishSubject.create();
    private final PublishSubject<String> jsonEmitter = PublishSubject.create();
    private final PublishSubject<NexoRequest> requestEmitter = PublishSubject.create();
    private final PublishSubject<NexoCommsStatus> commsStatusEmitter = PublishSubject.create();

    private NexoFlow nexoFlow = new NexoFlow();

    private NexoState state = NexoState.CLOSED;
    private LoginRequest loginRequest;
    private NexoDeserialiser deserialiser = new NexoDeserialiser();

    public void sendXmlMessage(String xml) {
        sendMessage(xml, MessageFormat.XML);
    }

    public void sendJSONMessage(String json) {
        sendMessage(json, MessageFormat.JSON);
    }

    public void sendMessage(String message, MessageFormat format) {
        SaleToPOIRequest saleToPOIRequest = deserialiser.deserialise(message, format);
        if (saleToPOIRequest != null) {
            Object request = nexoFlow.decodeNexoRequest(saleToPOIRequest, loginRequest);
            if (NexoState.OPEN.equals(state) || request instanceof LoginNotRequired) {
                emit(saleToPOIRequest, request, format);
            } else if (NexoState.CLOSED.equals(state)) {
                emit(saleToPOIRequest, new RejectedRequest(saleToPOIRequest, ErrorCondition.LOGGED_OUT), format);
            } else {
                emit(saleToPOIRequest, new RejectedRequest(saleToPOIRequest, ErrorCondition.BUSY), format);
            }
        }
    }

    private void sendAppflowObject(SaleToPOIRequest request, Object object, MessageFormat format) {
        System.err.println("BORTLES: Sending appflow object " + object);
        SaleToPOIResponse response = nexoFlow.encodeAppFlowObject(request, object);
        System.err.println("BORTLES: Encoded response is " + response);
        if (response != null) {
            switch (format) {
                case JSON:
                    String json = deserialiser.serialiseRequestJSON(response);
                    if (json != null) {
                        jsonEmitter.onNext(json);
                    }
                    break;
                case XML:
                    String xml = deserialiser.serialiseRequest(response);
                    if (xml != null) {
                        xmlEmitter.onNext(xml);
                    }
                    break;
            }
        }
    }

    public @NonNull Observable<NexoCommsStatus> getCommsStatus() {
        return commsStatusEmitter;
    }

    public @NonNull Observable<String> getOutputXML() {
        return xmlEmitter;
    }

    public @NonNull Observable<String> getOutputJSON() {
        return jsonEmitter;
    }

    public @NonNull Observable<NexoRequest> getRequests() {
        return requestEmitter;
    }

    private void emit(SaleToPOIRequest nexoRequest, Object request, MessageFormat format) {
        // Handle special cases
        if (request instanceof Login) {
            if (login((Login) request)) {
                sendAppflowObject(nexoRequest, new LoggedIn(), format);
            } else {
                sendAppflowObject(nexoRequest, new LoginFailure(), format);
            }
        } else if (request instanceof Logout) {
            if (logout((Logout) request)) {
                sendAppflowObject(nexoRequest, new LoggedOut(), format);
            } else {
                sendAppflowObject(nexoRequest, new LogoutFailure(), format);
            }
        } else if (request instanceof RejectedRequest) {
            sendAppflowObject(nexoRequest, request, format);
        }

        requestEmitter.onNext(createRequest(nexoRequest, request, format));
    }

    private NexoRequest createRequest(final SaleToPOIRequest nexoRequest, Object request, MessageFormat format) {
        return new NexoRequest(request) {
            @Override
            public void sendResponse(Object response) {
                sendAppflowObject(nexoRequest, response, format);
            }
        };
    }

    private boolean login(Login login) {
        // For an initial run at this just accept all logins from the default state. For future
        // reference:
        // 1) If configured, the Sale Server has to be successfully logged to the POI Server,
        //    before a Sale Terminal may be logged to a POI Terminal.
        // 2) Several Sale Terminals could be logged in to one POI Terminal, and one Sale Terminal
        //    could be logged in to several POI Terminals.
        // 3) The POI may send Display, Input or Print messages during the processing of the Login
        //    request message, before sending the Login response.
        // 4) Logout exchange can be skipped by a new Login. The POI shall accept a Login request
        //    message from the same Sale Component already logged in. All information of the new
        //    Login replaces those stored during the previous Login.
        // 5) Login cannot be aborted, but supports retry. If the Sale System does not receive a
        //    response, it can send another Login with a new message identifier ServiceID. Then the
        //    POI has not to answer to the first Login message, but only to the second one.
        // 6) Another error resolution for no reception of Login response is to send a Logout. Then
        //    the POI has not to answer to the first Login message, but only to the Logout, after
        //    stopped the Login process and completed the Logout process.
        // 7) The POI receives from the same Sale terminal, a Login request during the processing
        //    of the previous Login request which is not completed. The POI must continue the Login
        //    processing and answer to the last Login.
        // 8) When the cash handling machine managed by the POI does not have any more coins or
        //    bills of a certain value, the Login response must contain the related occurrence of
        //    CoinsOrBills with Number equal to 0 (in order to have the possible type of coins or
        //    notes accepted by the cash machine).
        // Note that some of these rules will not be relevant as long as login processing is
        // synchronous...
        if (NexoState.CLOSED.equals(state)) {
            loginRequest = login.getLoginRequest();
            state = NexoState.OPEN;
            return true;
        } else {
            return false;
        }
    }

    private boolean logout(Logout logout) {
        // For future reference as above:
        // 1) The Sale System has to wait for the response of the messages in progress before to
        //    send the Logout request. If there are transactions in progress on the POI Terminal or
        //    the Sale Terminal, the POI Terminal refuses the Logout, and a Logout response is sent
        //    with Result=”Failure” and ErrorCondition=” Busy”
        // 2) The POI may send Display, Input or Print message during the processing of the Logout
        //    request message, before to send the Logout response.
        // 3) The POI must to accept Logout request messages if the Sale Terminal (or Sale System)
        //    was not logged in.
        if (NexoState.OPEN.equals(state)) {
            loginRequest = null;
            state = NexoState.CLOSED;
            return true;
        } else {
            return false;
        }
    }

    private POISystemData createPOISystemData() {
        POISoftware poiSoftware = new POISoftware();
        poiSoftware.setApplicationName("NexLator");
        poiSoftware.setComponentDescription("Nexo to AppFlow bridge");
        poiSoftware.setComponentType("Bridge");
        poiSoftware.setProviderIdentification("AEVI");
        poiSoftware.setSoftwareVersion("0.0.1-SNAPSHOT");

        POIStatus poiStatus = new POIStatus();
        poiStatus.setCardReaderOKFlag(true);
        poiStatus.setPrinterStatus(PrinterStatus.OK.value());

        POISystemData systemData = new POISystemData();
        systemData.setDateTime(ZonedDateTime.now());
        systemData.setPOISoftware(poiSoftware);
        systemData.setPOIStatus(poiStatus);
        return systemData;
    }
}
