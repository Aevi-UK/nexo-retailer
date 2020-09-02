package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.nexo.model.SaleToPOIRequest;

public enum RequestType {
    ABORT,
    ADMIN,
    BALANCE_INQUIRY,
    BATCH,
    CARD_ACQUISITION,
    APDU,
    CARD_READER_INIT,
    CARD_READER_POWER_OFF,
    DIAGNOSIS,
    DISPLAY,
    ENABLE_SERVICE,
    EVENT_NOTIFICATION,
    GET_TOTALS,
    INPUT,
    INPUT_UPDATE,
    LOGIN,
    LOGOUT,
    LOYALTY,
    PAYMENT,
    PIN,
    PRINT,
    RECONCILIATION,
    REVERSAL,
    SOUND,
    STORED_VALUE,
    TRANSACTION_REPORT,
    TRANSACTION_STATUS,
    TRANSMIT,
    INVALID_REQUEST;

    public static RequestType requestTypeFromSaleRequest(SaleToPOIRequest request) {
        if (request.getAbortRequest() != null) {
            return ABORT;
        } else if (request.getAdminRequest() != null) {
            return ADMIN;
        } else if (request.getBalanceInquiryRequest() != null) {
            return BALANCE_INQUIRY;
        } else if (request.getBatchRequest() != null) {
            return BATCH;
        } else if (request.getCardAcquisitionRequest() != null) {
            return CARD_ACQUISITION;
        } else if (request.getCardReaderAPDURequest() != null) {
            return APDU;
        } else if (request.getCardReaderInitRequest() != null) {
            return CARD_READER_INIT;
        } else if (request.getCardReaderPowerOffRequest() != null) {
            return CARD_READER_POWER_OFF;
        } else if (request.getDiagnosisRequest() != null) {
            return DIAGNOSIS;
        } else if (request.getDisplayRequest() != null) {
            return DISPLAY;
        } else if (request.getEnableServiceRequest() != null) {
            return ENABLE_SERVICE;
        } else if (request.getEventNotification() != null) {
            return EVENT_NOTIFICATION;
        } else if (request.getGetTotalsRequest() != null) {
            return GET_TOTALS;
        } else if (request.getInputRequest() != null) {
            return INPUT;
        } else if (request.getInputUpdate() != null) {
            return INPUT_UPDATE;
        } else if (request.getLoginRequest() != null) {
            return LOGIN;
        } else if (request.getLogoutRequest() != null) {
            return LOGOUT;
        } else if (request.getLoyaltyRequest() != null) {
            return LOYALTY;
        } else if (request.getPaymentRequest() != null) {
            return PAYMENT;
        } else if (request.getPINRequest() != null) {
            return PIN;
        } else if (request.getPrintRequest() != null) {
            return PRINT;
        } else if (request.getReconciliationRequest() != null) {
            return RECONCILIATION;
        } else if (request.getReversalRequest() != null) {
            return REVERSAL;
        } else if (request.getSoundRequest() != null) {
            return SOUND;
        } else if (request.getStoredValueRequest() != null) {
            return STORED_VALUE;
        } else if (request.getTransactionReportRequest() != null) {
            return TRANSACTION_REPORT;
        } else if (request.getTransactionStatusRequest() != null) {
            return TRANSACTION_STATUS;
        } else if (request.getTransmitRequest() != null) {
            return TRANSMIT;
        }
        
        return INVALID_REQUEST;
    }
}
