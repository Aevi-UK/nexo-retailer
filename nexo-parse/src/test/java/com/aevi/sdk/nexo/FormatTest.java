package com.aevi.sdk.nexo;

import com.aevi.sdk.nexo.model.SaleToPOIResponseType;

import org.junit.Test;

import io.reactivex.observers.TestObserver;

public class FormatTest extends ResponseTest {
    private static final String LOGIN_JSON = "{\n"
            + "\"SaleToPOIRequest\":{\n"
            + "\"MessageHeader\":{\n"
            + "\"ProtocolVersion\":\"3.0\",\n"
            + "\"MessageClass\":\"Service\",\n"
            + "\"MessageCategory\":\"Login\",\n"
            + "\"MessageType\":\"Request\",\n"
            + "\"ServiceID\":\"498\",\n"
            + "\"SaleID\":\"SaleTermA\",\n"
            + "\"POIID\":\"POITerm1\"\n"
            + "},\n"
            + "\"LoginRequest\":{\n"
            + "\"DateTime\":\"2009-01-29T09:13:51.0+01:00\",\n"
            + "\"SaleSoftware\":{\n"
            + "\"ProviderIdentification\":\"PointOfSaleCo\",\n"
            + "\"ApplicationName\":\"SaleSys\",\n"
            + "\"SoftwareVersion\":\"01.98.01\",\n"
            + "\"CertificationCode\":\"ECTS2PS001\"\n"
            + "},\n"
            + "\"SaleTerminalData\":{\n"
            + "\"TerminalEnvironment\":\"Attended\",\n"
            + "\"SaleCapabilities\":[\"PrinterReceipt\",\"CashierStatus\",\"CashierError\",\n"
            + "\"CashierDisplay\",\"CashierInput\"]\n"
            + "},\n"
            + "\"TrainingModeFlag\":true,\n"
            + "\"OperatorLanguage\":\"sp\",\n"
            + "\"OperatorID\":\"Cashier16\",\n"
            + "\"ShiftNumber\":\"2\",\n"
            + "\"POISerialNumber\":\"78910AA46010005\"\n"
            + "}\n"
            + "}\n"
            + "}";

    @Test
    public void jsonReturnsJson() {
        TestObserver<String> responseObserver = new TestObserver<>();
        NexoManager manager = new NexoManager();
        manager.getOutputJSON().subscribe(responseObserver);
        manager.sendJSONMessage(LOGIN_JSON);

        responseObserver.assertNoErrors();
        responseObserver.assertValueCount(1);
        responseObserver.assertValueAt(0, s -> {
            SaleToPOIResponseType response = deserialiser.deserialiseResponseJSON(s);
            return response != null
                    && response.getLoginResponse() != null;
        });
    }

    @Test
    public void xmlReturnsXml() {
        TestObserver<String> responseObserver = new TestObserver<>();
        NexoManager manager = new NexoManager();
        manager.getOutputXML().subscribe(responseObserver);
        manager.sendXmlMessage(LOGIN);

        responseObserver.assertNoErrors();
        responseObserver.assertValueCount(1);
        responseObserver.assertValueAt(0, s -> {
            SaleToPOIResponseType response = parseResponse(s);
            return response != null
                    && response.getLoginResponse() != null;
        });
    }
}
