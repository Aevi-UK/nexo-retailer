package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.nexo.extramodel.RejectedRequest;
import com.aevi.sdk.nexo.translators.NexoToAppFlow;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;

public class SaleRequestTranslator implements NexoToAppFlow<SaleToPOIRequest, Object> {
    private static final PaymentRequestTranslator PAYMENT_REQUEST_TRANSLATOR = new PaymentRequestTranslator();
    private static final LoginRequestTranslator LOGIN_REQUEST_TRANSLATOR = new LoginRequestTranslator();
    private static final LogoutRequestTranslator LOGOUT_REQUEST_TRANSLATOR = new LogoutRequestTranslator();

    @Override
    public Object translate(SaleToPOIRequest nexoObject) {
        RequestType type = RequestType.requestTypeFromSaleRequest(nexoObject);
        if (nexoObject.getPaymentRequest() != null) {
            return PAYMENT_REQUEST_TRANSLATOR.translate(nexoObject.getPaymentRequest());
        } else if (nexoObject.getAbortRequest() != null) {
            // ABORT
        } else if (nexoObject.getAdminRequest() != null) {
            // ADMIN
        } else if (nexoObject.getBalanceInquiryRequest() != null) {
            // BALANCE INQUIRY
        } else if (nexoObject.getBatchRequest() != null) {
            // BATCH
        } else if (nexoObject.getCardAcquisitionRequest() != null) {
            // CARD ACQUISITION
        } else if (nexoObject.getCardReaderAPDURequest() != null) {
            // APDU
        } else if (nexoObject.getCardReaderInitRequest() != null) {
            // CARD READER INIT
        } else if (nexoObject.getCardReaderPowerOffRequest() != null) {
            // CARD READER POWER OFF
        } else if (nexoObject.getDiagnosisRequest() != null) {
            // DIAGNOSIS
        } else if (nexoObject.getDisplayRequest() != null) {
            // DISPLAY
        } else if (nexoObject.getEnableServiceRequest() != null) {
            // ENABLE SERVICE
        } else if (nexoObject.getEventNotification() != null) {
            // EVENT NOTIFICATION
        } else if (nexoObject.getGetTotalsRequest() != null) {
            // GET TOTALS
        } else if (nexoObject.getInputRequest() != null) {
            // INPUT
        } else if (nexoObject.getInputUpdate() != null) {
            // INPUT UPDATE
        } else if (nexoObject.getLoginRequest() != null) {
            return LOGIN_REQUEST_TRANSLATOR.translate(nexoObject.getLoginRequest());
        } else if (nexoObject.getLogoutRequest() != null) {
            return LOGOUT_REQUEST_TRANSLATOR.translate(nexoObject.getLogoutRequest());
        } else if (nexoObject.getLoyaltyRequest() != null) {
            // LOYALTY
        } else if (nexoObject.getPINRequest() != null) {
            // PIN
        } else if (nexoObject.getPrintRequest() != null) {
            // PRINT
        } else if (nexoObject.getReconciliationRequest() != null) {
            // RECONCILIATION
        } else if (nexoObject.getReversalRequest() != null) {
            // REVERSAL
        } else if (nexoObject.getSoundRequest() != null) {
            // SOUND (!)
        } else if (nexoObject.getStoredValueRequest() != null) {
            // STORED VALUE
        } else if (nexoObject.getTransactionReportRequest() != null) {
            // TRANSACTION REPORT
        } else if (nexoObject.getTransactionStatusRequest() != null) {
            // TRANSACTION STATUS
        } else if (nexoObject.getTransmitRequest() != null) {
            // TRANSMIT
        }

        // Not doing anything with message header or security trailer yet...

        // If nothing was translated, return an unsupported request...
        return new RejectedRequest(nexoObject);
    }
}
