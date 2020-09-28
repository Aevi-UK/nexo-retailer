package com.aevi.sdk.nexo;

import com.aevi.sdk.nexo.model.ObjectFactory;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIRequestType;
import com.aevi.sdk.nexo.translators.requests.SaleRequestTranslator;

import org.junit.Test;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class NexoTest {
    private static final String SIMPLE_PURCHASE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<SaleToPOIRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "xsi:noNamespaceSchemaLocation=\"NexoSaleToPOIMessages.xsd\">\n"
            + "<MessageHeader MessageClass=\"Service\" MessageCategory=\"Payment\"\n"
            + "MessageType=\"Request\" ServiceID=\"642\" SaleID=\"SaleTermA\" POIID=\"POITerm1\"/>\n"
            + "<PaymentRequest>\n"
            + "<SaleData>\n"
            + "<SaleTransactionID TransactionID=\"580\" TimeStamp=\"2010-06-\n"
            + "10T22:53:12.6+01:00\"/>\n"
            + "</SaleData>\n"
            + "<PaymentTransaction>\n"
            + "<AmountsReq Currency=\"EUR\" RequestedAmount=\"31.00\"/>\n"
            + "<TransactionConditions LoyaltyHandling=\"Forbidden\"/>\n"
            + "</PaymentTransaction>\n"
            + "</PaymentRequest>\n"
            + "</SaleToPOIRequest>";

    private static final String PURCHASE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<SaleToPOIRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "xsi:noNamespaceSchemaLocation=\"NexoSaleToPOIMessages.xsd\">\n"
            + "<MessageHeader MessageClass=\"Service\" MessageCategory=\"Payment\"\n"
            + "MessageType=\"Request\" ServiceID=\"643\" SaleID=\"SaleTermA\" POIID=\"POITerm1\"/>\n"
            + "<PaymentRequest>\n"
            + "<SaleData>\n"
            + "<SaleTransactionID TransactionID=\"581\" TimeStamp=\"2015-04-\n"
            + "06T10:42:34.1+01:00\"/>\n"
            + "</SaleData>\n"
            + "<PaymentTransaction>\n"
            + "<AmountsReq Currency=\"EUR\" RequestedAmount=\"38.52\"/>\n"
            + "<TransactionConditions LoyaltyHandling=\"Forbidden\"/>\n"
            + "<SaleItem ItemID=\"1\" ProductCode=\"673\" EanUpc=\"84116369\" ItemAmount=\"2.30\">\n"
            + "<Quantity>2</Quantity>\n"
            + "<UnitPrice>1.15</UnitPrice>\n"
            + "<ProductLabel>Mineral Water 1,5L</ProductLabel>\n"
            + "</SaleItem>\n"
            + "<SaleItem ItemID=\"2\" ProductCode=\"101\" ItemAmount=\"33.22\">\n"
            + "<UnitOfMeasure>Litre</UnitOfMeasure>\n"
            + "<Quantity>38.23</Quantity>\n"
            + "<UnitPrice>0.869</UnitPrice>\n"
            + "<ProductLabel>Diesel Fuel</ProductLabel>\n"
            + "</SaleItem>\n"
            + "</PaymentTransaction>\n"
            + "<PaymentData PaymentType=\"Normal\"/>\n"
            + "</PaymentRequest>\n"
            + "</SaleToPOIRequest>";

    @Test
    public void testNexoPayment() throws Exception {
        Object translated = parse(SIMPLE_PURCHASE);
        System.out.println(translated);
    }

    @Test
    public void testNexoPaymentWithBasket() throws Exception {
        Object translated = parse(PURCHASE);
        System.out.println(translated);
    }

    private Object parse(String xml) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        SaleToPOIRequestType parsed = (SaleToPOIRequestType) jaxbUnmarshaller.unmarshal(new StringReader(xml));

        SaleRequestTranslator saleRequestTranslator = new SaleRequestTranslator();
        return saleRequestTranslator.translate(new SaleToPOIRequest(parsed));
    }
}
