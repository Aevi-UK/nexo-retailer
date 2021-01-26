package com.aevi.sdk.nexo;

import com.aevi.sdk.nexo.model.NexoDeserialiser;
import com.aevi.sdk.nexo.model.NexoException;
import com.aevi.sdk.nexo.model.Response;
import com.aevi.sdk.nexo.model.SaleToPOIResponseType;

public abstract class ResponseTest {
    protected static final String LOGIN = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<SaleToPOIRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "xsi:noNamespaceSchemaLocation=\"nexoSaleToPOIMessages.xsd\">\n"
            + "<MessageHeader ProtocolVersion=\"3.0\" MessageClass=\"Service\"\n"
            + "MessageCategory=\"Login\" MessageType=\"Request\" ServiceID=\"498\"\n"
            + "SaleID=\"SaleTermA\" POIID=\"POITerm1\"/>\n"
            + "<LoginRequest OperatorLanguage=\"sp\" OperatorID=\"Cashier16\" ShiftNumber=\"2\"\n"
            + "POISerialNumber=\"78910AA46010005\">\n"
            + "<DateTime>2015-03-08T09:13:51.0+01:00</DateTime>\n"
            + "<SaleSoftware ProviderIdentification=\"PointOfSaleCo\" ApplicationName=\"SaleSys\"\n"
            + "SoftwareVersion=\"01.98.01\" CertificationCode=\"ECTS2PS001\"/>\n"
            + "<SaleTerminalData TerminalEnvironment=\"Attended\">\n"
            + "<SaleCapabilities>PrinterReceipt CashierStatus CashierError CashierDisplay\n"
            + "CashierInput</SaleCapabilities>\n"
            + "<SaleProfile GenericProfile=\"Extended\">\n"
            + "<ServiceProfiles>Loyalty PIN CardReader</ServiceProfiles>\n"
            + "</SaleProfile>\n"
            + "</SaleTerminalData>\n"
            + "</LoginRequest>\n"
            + "</SaleToPOIRequest>";

    protected NexoDeserialiser deserialiser = new NexoDeserialiser();

    protected SaleToPOIResponseType parseResponse(String xml) throws NexoException {
        return deserialiser.deserialiseResponseXML(xml);
    }

    protected boolean isSuccess(Response response) {
        return  response != null && "Success".equals(response.getResult());
    }
}
