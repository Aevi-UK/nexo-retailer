package com.aevi.sdk.nexo;

import com.aevi.sdk.pos.flow.model.Payment;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.observers.TestObserver;

public class PaymentTest extends ResponseTest {
    private static final String SIMPLE_PAYMENT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<SaleToPOIRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
            + "xsi:noNamespaceSchemaLocation=\"NexoSaleToPOIMessages.xsd\">\n"
            + "<MessageHeader MessageClass=\"Service\" MessageCategory=\"Payment\"\n"
            + "MessageType=\"Request\" ServiceID=\"642\" SaleID=\"SaleTermA\" POIID=\"POITerm1\"/>\n"
            + "<PaymentRequest>\n"
            + "<SaleData>\n"
            + "<SaleTransactionID TransactionID=\"579\" TimeStamp=\"2009-03-10T23:08:42.4+01:00\"/>\n"
            + "</SaleData>\n"
            + "<PaymentTransaction>\n"
            + "<AmountsReq Currency=\"EUR\" RequestedAmount=\"104.11\"/>\n"
            + "<TransactionConditions LoyaltyHandling=\"Forbidden\"/>\n"
            + "</PaymentTransaction>\n"
            + "<PaymentData PaymentType=\"Normal\"/>\n"
            + "</PaymentRequest>\n"
            + "</SaleToPOIRequest>";
    private NexoManager nexoManager;

    @Before
    public void loginManager() {
        nexoManager = new NexoManager();
        nexoManager.sendXmlMessage(LOGIN);
    }

    @Test
    public void simplePayment() {
        TestObserver<NexoRequest> testObserver = new TestObserver();
        nexoManager.getRequests().subscribe(testObserver);

        nexoManager.sendXmlMessage(SIMPLE_PAYMENT);

        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValueAt(0, nexoRequest -> {
            if (nexoRequest.isAppFlowPayment()) {
                Payment payment = nexoRequest.getAsAppFlowPayment();
                if (payment.getAmounts() != null) {
                }
            }
            return false;
        });
    }
}
