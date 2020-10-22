package com.aevi.sdk.nexo;

import com.aevi.sdk.flow.constants.FlowTypes;
import com.aevi.sdk.flow.constants.events.FlowEventTypes;
import com.aevi.sdk.flow.model.FlowEvent;
import com.aevi.sdk.nexo.extramodel.requests.Login;
import com.aevi.sdk.nexo.extramodel.requests.Logout;
import com.aevi.sdk.nexo.model.MessageFormat;
import com.aevi.sdk.pos.flow.model.Amounts;
import com.aevi.sdk.pos.flow.model.Payment;
import com.aevi.sdk.pos.flow.model.PaymentBuilder;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.observers.TestObserver;

public class SampleMessagesTest extends ResponseTest {
    private static final String PREFIX_REQUESTS = "requests/";
    private static final String PREFIX_RESPONSES = "responses/";
    private static final String PREFIX_TRANSLATED = "translatedRequests/";

    @Test
    public void testAbort() throws IOException {
        testSampleMessage("abort.xml", new FlowEvent(FlowEventTypes.EVENT_NOTIFY_ACTION), null);
    }

    @Test
    public void testAdmin() throws IOException {
        testUnsupported("admin.xml");
    }

    @Test
    public void testBalanceInquiry() throws IOException {
        testUnsupported("balanceInquiry.xml");
    }

    @Test
    public void testBatch() throws IOException {
        testUnsupported("batch.xml");
    }

    @Test
    public void testCardAcquisition() throws IOException {
        testUnsupported("cardAcquisition.xml");
    }

    @Test
    public void testCardAPDU() throws IOException {
        testUnsupported("cardAPDU.xml");
    }

    @Test
    public void testCardInit() throws IOException {
        testUnsupported("cardInit.xml");
    }

    @Test
    public void testCardPowerOff() throws IOException {
        testUnsupported("cardPowerOff.xml");
    }

    @Test
    public void testDiagnosis() throws IOException {
        testUnsupported("diagnosis.xml");
    }

    @Test
    public void testDisplay() throws IOException {
        testUnsupported("display.xml");
    }

    @Test
    public void testEnableService() throws IOException {
        testUnsupported("enableService.xml");
    }

// Shouldn't come from client, not currently supported, no response...
//    @Test
//    public void testEventNotification() throws IOException {
//        testUnsupported("eventNotification.xml");
//    }

    @Test
    public void testGetTotals() throws IOException {
        testUnsupported("getTotals.xml");
    }

    @Test
    public void testInputConfirmation() throws IOException {
        testUnsupported("inputConfirmation.xml");
    }

    @Test
    public void testInputMenu() throws IOException {
        testUnsupported("inputMenu.xml");
    }

    @Test
    public void testInputRequest() throws IOException {
        testUnsupported("inputRequest.xml");
    }

    @Test
    public void testInputUpdate() throws IOException {
        testUnsupported("inputUpdate.xml");
    }

    @Test
    public void testLoginXml() throws IOException {
        testSampleMessage("login.xml", true, new Login(null), "login.xml", false);
    }

    @Test
    public void testLoginJson() throws IOException {
        testSampleMessage("login.json", true, new Login(null), "login.json", false);
    }

    @Test
    public void testLogout() throws IOException {
        testSampleMessage("logout.xml", true, new Logout(), "logout.xml", true);
    }

    @Test
    public void testLoyaltyAwardRefund() throws IOException {
        testUnsupported("loyaltyAwardRefund.xml");
    }

    @Test
    public void testLoyaltyWithPayment() throws IOException {
        testSendAppflowMessage("loyaltyWithPayment.xml", createFakePayment());
    }

    @Test
    public void testPaymentBasket() throws IOException {
        testSendAppflowMessage("paymentBasket.xml", createFakePayment());
    }

    @Test
    public void testPaymentLocalCard() throws IOException {
        testSendAppflowMessage("paymentLocalCard.xml", createFakePayment());
    }

    @Test
    public void testPaymentProtectedCardData() throws IOException {
        testSendAppflowMessage("paymentProtectedCardData.xml", createFakePayment());
    }

    @Test
    public void testPaymentSimple() throws IOException {
        testSendAppflowMessage("paymentSimple.xml", createFakePayment());
    }

    @Test
    public void testPin() throws IOException {
        testUnsupported("pin.xml");
    }

    @Test
    public void testPrint() throws IOException {
        testUnsupported("print.xml");
    }

    @Test
    public void testReconciliation() throws IOException {
        testUnsupported("reconciliation.xml");
    }

    @Test
    public void testReversal() throws IOException {
        testUnsupported("reversal.xml");
    }

    @Test
    public void testSound() throws IOException {
        testUnsupported("sound.xml");
    }

    @Test
    public void testStoredValue() throws IOException {
        testUnsupported("storedValue.xml");
    }

    @Test
    public void testTransactionStatus() throws IOException {
        testUnsupported("transactionStatus.xml");
    }

    @Test
    public void testTransmit() throws IOException {
        testUnsupported("transmit.xml");
    }

    private void testUnsupported(String input) throws IOException {
        testSampleMessage(input, false, null, null, true);
    }

    private void testSendAppflowMessage(String input, Object expected) throws IOException {
        testSampleMessage(input, true, expected, null, true);
    }

    private void testSendImmediateResponse(String input, String response) throws IOException {
        testSampleMessage(input, true, null, response, true);
    }

    private void testSampleMessage(String input, Object expected, String output) throws IOException {
        testSampleMessage(input, true, expected, output, true);
    }

    private void testSampleMessage(String input, boolean supported, Object expected, String output, boolean login) throws IOException {
        NexoManager nexoManager = new NexoManager();

        if (login) {
            nexoManager.sendXmlMessage(LOGIN);
        }

        TestObserver<NexoRequest> testObserver = new TestObserver();
        TestObserver<String> xmlOutput = new TestObserver();
        TestObserver<String> jsonOutput = new TestObserver();
        nexoManager.getRequests().subscribe(testObserver);
        nexoManager.getOutputJSON().subscribe(jsonOutput);
        nexoManager.getOutputXML().subscribe(xmlOutput);

        String message = loadResource(PREFIX_REQUESTS + input);
        nexoManager.sendMessage(message, formatFromName(input));

        testObserver.assertNoErrors();
        xmlOutput.assertNoErrors();
        jsonOutput.assertNoErrors();

        if (expected == null) {
            testObserver.assertValueCount(0);
        } else {
            testObserver.assertValueCount(1);
            testObserver.assertValueAt(0,
                    nexoRequest -> {
                        System.err.println("Comparing " + expected + " to " + nexoRequest.getRaw());
                        return expected.getClass() == nexoRequest.getRaw().getClass();
                    });
        }

        if (supported) {
            if (output == null) {
                xmlOutput.assertValueCount(0);
                jsonOutput.assertValueCount(0);
            } else {
                TestObserver<String> outputObserver = jsonOutput.valueCount() > 0 ? jsonOutput : xmlOutput;
                TestObserver<String> otherObserver = jsonOutput.valueCount() > 0 ? xmlOutput : jsonOutput;
                outputObserver.assertValueCount(1);
                otherObserver.assertValueCount(0);
                outputObserver.assertValueAt(0,
                        outputStr -> {
                            String expectedStr = loadResource(PREFIX_RESPONSES + output);
                            System.err.println("Comparing '" + outputStr + "' to '" + expectedStr + "'");
                            return expectedStr != null && expectedStr.equals(outputStr);
                        });
                // output should match expected output
            }
        } else {
            TestObserver<String> outputObserver = jsonOutput.valueCount() > 0 ? jsonOutput : xmlOutput;
            TestObserver<String> otherObserver = jsonOutput.valueCount() > 0 ? xmlOutput : jsonOutput;

            otherObserver.assertValueCount(0);
            outputObserver.assertValueCount(1);

            outputObserver.assertValueAt(0,
                    outputStr -> {
                System.err.println(outputStr);
                        // TODO check that it's a rejected (unsupported) operation
                        return true;
                    });
        }
    }

    private MessageFormat formatFromName(String fileName) {
        return fileName.endsWith(".json") ? MessageFormat.JSON : MessageFormat.XML;
    }

    private String loadResource(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String nextLine = reader.readLine();
        while (nextLine != null) {
            if (builder.length() > 0) {
                builder.append('\n');
            }
            builder.append(nextLine);
            nextLine = reader.readLine();
        }
        return builder.toString();
    }

    // TODO get rid of this
    private Payment createFakePayment() {
        return new PaymentBuilder().withPaymentFlow(FlowTypes.FLOW_TYPE_SALE).withAmounts(new Amounts(100, "EUR")).build();
    }
}
