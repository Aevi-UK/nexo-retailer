package com.aevi.sdk.nexo.model;

public class SaleToPOIRequest extends SaleToPOIRequestType {
    public ResponseHolder createResponseHolder() {
        if (getAdminRequest() != null) {
            return new AdminResponse();
        } else if (getBalanceInquiryRequest() != null) {
            return new BalanceInquiryResponse();
        } else if (getBatchRequest() != null) {
            return new BatchResponse();
        } else if (getCardAcquisitionRequest() != null) {
            return new CardAcquisitionResponse();
        } else if (getCardReaderAPDURequest() != null) {
            return new CardReaderAPDUResponse();
        } else if (getCardReaderInitRequest() != null) {
            return new CardReaderInitResponse();
        } else if (getCardReaderPowerOffRequest() != null) {
            return new CardReaderPowerOffResponse();
        } else if (getDiagnosisRequest() != null) {
            return new DiagnosisResponse();
        } else if (getEnableServiceRequest() != null) {
            return new EnableServiceResponse();
        } else if (getGetTotalsRequest() != null) {
            return new GetTotalsResponse();
        } else if (getLoginRequest() != null) {
            return new LoginResponse();
        } else if (getLogoutRequest() != null) {
            return new LogoutResponse();
        } else if (getLoyaltyRequest() != null) {
            return new LoyaltyResponse();
        } else if (getPaymentRequest() != null) {
            return new PaymentResponse();
        } else if (getPINRequest() != null) {
            return new PINResponse();
        } else if (getPrintRequest() != null) {
            return new PrintResponse();
        } else if (getReconciliationRequest() != null) {
            return new ReconciliationResponse();
        } else if (getReversalRequest() != null) {
            return new ReversalResponse();
        } else if (getSoundRequest() != null) {
            return new SoundResponse();
        } else if (getStoredValueRequest() != null) {
            return new StoredValueResponse();
        } else if (getTransactionReportRequest() != null) {
            return new TransactionReportResponse();
        } else if (getTransactionStatusRequest() != null) {
            return new TransactionStatusResponse();
        } else if (getTransmitRequest() != null) {
            return new TransmitResponse();
        }
        return null;
    }
}
