package com.aevi.sdk.nexo;

import com.aevi.sdk.nexo.model.NexoException;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;
import com.aevi.sdk.nexo.model.SaleToPOIResponseType;

import org.junit.Test;

import io.reactivex.observers.TestObserver;

public class LoginTest extends ResponseTest {
    private static final String LOGOUT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<SaleToPOIRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "xsi:noNamespaceSchemaLocation=\"NexoSaleToPOIMessages.xsd\">\n"
            + "<MessageHeader MessageClass=\"Service\" MessageCategory=\"Logout\"\n"
            + "MessageType=\"Request\" ServiceID=\"613\" SaleID=\"SaleTermA\" POIID=\"POITerm1\"/>\n"
            + "<LogoutRequest MaintenanceAllowed=\"true\"/>\n"
            + "</SaleToPOIRequest>";

    @Test
    public void loginShouldSucceed() throws NexoException {
        TestObserver<String> responseObserver = new TestObserver<>();
        NexoManager manager = new NexoManager();
        manager.getOutputXML().subscribe(responseObserver);
        manager.sendXmlMessage(LOGIN);

        responseObserver.assertNoErrors();
        responseObserver.assertValueCount(1);
        responseObserver.assertValueAt(0, s -> {
            SaleToPOIResponseType response = parseResponse(s);
            return response != null
                    && response.getLoginResponse() != null
                    && response.getLoginResponse().getResponse() != null
                    && isSuccess(response.getLoginResponse().getResponse());
        });
    }

    @Test
    public void doubleLoginShouldFail() throws NexoException {
        TestObserver<String> responseObserver = new TestObserver<>();
        NexoManager manager = new NexoManager();
        manager.getOutputXML().subscribe(responseObserver);

        manager.sendXmlMessage(LOGIN);
        manager.sendXmlMessage(LOGIN);

        responseObserver.assertNoErrors();
        responseObserver.assertValueCount(2);
        responseObserver.assertValueAt(0, s -> {
            SaleToPOIResponseType response = parseResponse(s);
            return response != null
                    && response.getLoginResponse() != null
                    && response.getLoginResponse().getResponse() != null
                    && isSuccess(response.getLoginResponse().getResponse());
        });
        responseObserver.assertValueAt(1, s -> {
            SaleToPOIResponseType response = parseResponse(s);
            return response != null
                    && response.getLoginResponse() != null
                    && response.getLoginResponse().getResponse() != null
                    && !isSuccess(response.getLoginResponse().getResponse());
        });
    }

    @Test
    public void initialLogoutShouldFail() throws NexoException {
        TestObserver<String> responseObserver = new TestObserver<>();
        NexoManager manager = new NexoManager();
        manager.getOutputXML().subscribe(responseObserver);
        manager.sendXmlMessage(LOGOUT);

        responseObserver.assertNoErrors();
        responseObserver.assertValueCount(1);
        responseObserver.assertValueAt(0, s -> {
            SaleToPOIResponseType response = parseResponse(s);
            return response != null
                    && response.getLogoutResponse() != null
                    && response.getLogoutResponse().getResponse() != null
                    && !isSuccess(response.getLogoutResponse().getResponse());
        });
    }

    @Test
    public void logoutAfterLoginShouldSucceed() throws NexoException {
        TestObserver<String> responseObserver = new TestObserver<>();
        NexoManager manager = new NexoManager();
        manager.getOutputXML().subscribe(responseObserver);
        manager.sendXmlMessage(LOGIN);
        manager.sendXmlMessage(LOGOUT);

        responseObserver.assertNoErrors();
        responseObserver.assertValueCount(2);
        responseObserver.assertValueAt(1, s -> {
            SaleToPOIResponseType response = parseResponse(s);
            return response != null
                    && response.getLogoutResponse() != null
                    && response.getLogoutResponse().getResponse() != null
                    && isSuccess(response.getLogoutResponse().getResponse());
        });
    }

    @Test
    public void doubleLogoutShouldFail() throws NexoException {
        TestObserver<String> responseObserver = new TestObserver<>();
        NexoManager manager = new NexoManager();
        manager.getOutputXML().subscribe(responseObserver);
        manager.sendXmlMessage(LOGIN);
        manager.sendXmlMessage(LOGOUT);
        manager.sendXmlMessage(LOGOUT);

        responseObserver.assertNoErrors();
        responseObserver.assertValueCount(3);
        responseObserver.assertValueAt(2, s -> {
            SaleToPOIResponseType response = parseResponse(s);
            return response != null
                    && response.getLogoutResponse() != null
                    && response.getLogoutResponse().getResponse() != null
                    && !isSuccess(response.getLogoutResponse().getResponse());
        });
    }

}
