package com.aevi.sdk.nexo.model;

public class SaleToPOIResponse extends SaleToPOIResponseType {
    public void setResponse(ResponseHolder responseHolder) {
        if (responseHolder instanceof AdminResponse) {
            setAdminResponse((AdminResponse) responseHolder);
        } else if (responseHolder instanceof BalanceInquiryResponse) {
            setBalanceInquiryResponse((BalanceInquiryResponse) responseHolder);
        } else if (responseHolder instanceof BatchResponse) {
            setBatchResponse((BatchResponse) responseHolder);
        } else if (responseHolder instanceof CardAcquisitionResponse) {
            setCardAcquisitionResponse((CardAcquisitionResponse) responseHolder);
        } else if (responseHolder instanceof CardReaderAPDUResponse) {
            setCardReaderAPDUResponse((CardReaderAPDUResponse) responseHolder);
        } else if (responseHolder instanceof CardReaderInitResponse) {
            setCardReaderInitResponse((CardReaderInitResponse) responseHolder);
        } else if (responseHolder instanceof CardReaderPowerOffResponse) {
            setCardReaderPowerOffResponse((CardReaderPowerOffResponse) responseHolder);
        } else if (responseHolder instanceof DiagnosisResponse) {
            setDiagnosisResponse((DiagnosisResponse) responseHolder);
        } else if (responseHolder instanceof EnableServiceResponse) {
            setEnableServiceResponse((EnableServiceResponse) responseHolder);
        } else if (responseHolder instanceof GetTotalsResponse) {
            setGetTotalsResponse((GetTotalsResponse) responseHolder);
        } else if (responseHolder instanceof LoginResponse) {
            setLoginResponse((LoginResponse) responseHolder);
        } else if (responseHolder instanceof LogoutResponse) {
            setLogoutResponse((LogoutResponse) responseHolder);
        } else if (responseHolder instanceof LoyaltyResponse) {
            setLoyaltyResponse((LoyaltyResponse) responseHolder);
        } else if (responseHolder instanceof PaymentResponse) {
            setPaymentResponse((PaymentResponse) responseHolder);
        } else if (responseHolder instanceof PINResponse) {
            setPINResponse((PINResponse) responseHolder);
        } else if (responseHolder instanceof PrintResponse) {
            setPrintResponse((PrintResponse) responseHolder);
        } else if (responseHolder instanceof ReconciliationResponse) {
            setReconciliationResponse((ReconciliationResponse) responseHolder);
        } else if (responseHolder instanceof ReversalResponse) {
            setReversalResponse((ReversalResponse) responseHolder);
        } else if (responseHolder instanceof SoundResponse) {
            setSoundResponse((SoundResponse) responseHolder);
        } else if (responseHolder instanceof StoredValueResponse) {
            setStoredValueResponse((StoredValueResponse) responseHolder);
        } else if (responseHolder instanceof TransactionReportResponse) {
            setTransactionReportResponse((TransactionReportResponse) responseHolder);
        } else if (responseHolder instanceof TransactionStatusResponse) {
            setTransactionStatusResponse((TransactionStatusResponse) responseHolder);
        } else if (responseHolder instanceof TransmitResponse) {
            setTransmitResponse((TransmitResponse) responseHolder);
        } else {
            throw new IllegalArgumentException("Unrecognised response type " + responseHolder);
        }
    }
}
