package com.aevi.sdk.nexo;

import com.aevi.sdk.nexo.model.NexoDeserialiser;
import com.aevi.sdk.nexo.model.NexoException;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.xmlunit.matchers.CompareMatcher;

import static org.junit.Assert.assertThat;

public class TestNexoDeserialiser {
    protected static final String XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
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
    private static final String JSON = "{\n"
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
    public void testDeserialiseJson() throws NexoException {
        NexoDeserialiser deserialiser = new NexoDeserialiser();
        SaleToPOIRequest request = deserialiser.deserialiseJSON(JSON);
    }

    @Test
    public void testRoundTripResponseJSON() throws Exception {
        NexoDeserialiser deserialiser = new NexoDeserialiser();

        SaleToPOIRequest request = deserialiser.deserialiseJSON(JSON);
        String json = deserialiser.serialiseRequestJSON(request);

        System.err.println("<= " + JSON);
        System.err.println("=> " + json);

        JSONAssert.assertEquals(JSON, json, true);
    }

    @Test
    public void testRoundTripResponseXML() throws Exception {
        NexoDeserialiser deserialiser = new NexoDeserialiser();

        SaleToPOIRequest request = deserialiser.deserialiseXML(XML);
        String xml = deserialiser.serialiseRequest(request);

        System.err.println("<= " + XML);
        System.err.println("=> " + xml);

        assertThat(xml, CompareMatcher.isIdenticalTo(XML).ignoreWhitespace());
    }
}
