package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.flow.constants.AmountIdentifiers;
import com.aevi.sdk.flow.constants.PaymentMethods;
import com.aevi.sdk.nexo.extramodel.responses.LoginFailure;
import com.aevi.sdk.nexo.model.AmountsResp;
import com.aevi.sdk.nexo.model.CardData;
import com.aevi.sdk.nexo.model.LoginResponse;
import com.aevi.sdk.nexo.model.MessageCategory;
import com.aevi.sdk.nexo.model.MessageHeader;
import com.aevi.sdk.nexo.model.MessageType;
import com.aevi.sdk.nexo.model.POIData;
import com.aevi.sdk.nexo.model.PaymentAcquirerData;
import com.aevi.sdk.nexo.model.PaymentInstrumentData;
import com.aevi.sdk.nexo.model.PaymentInstrumentType;
import com.aevi.sdk.nexo.model.PaymentRequest;
import com.aevi.sdk.nexo.model.PaymentResult;
import com.aevi.sdk.nexo.model.SaleData;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;
import com.aevi.sdk.nexo.model.SaleToPOIResponseType;
import com.aevi.sdk.nexo.model.TransactionIdentification;
import com.aevi.sdk.pos.flow.model.Amounts;
import com.aevi.sdk.pos.flow.model.Card;
import com.aevi.sdk.pos.flow.model.PaymentResponse;
import com.aevi.sdk.pos.flow.model.TransactionResponse;

import java.math.BigDecimal;

public class PaymentResponseTranslator extends ResponseTranslator<PaymentResponse, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(SaleToPOIRequest originalRequest, PaymentResponse appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
        saleToPOIResponse.setMessageHeader(copyMessageHeader(originalRequest));
        com.aevi.sdk.nexo.model.PaymentResponse paymentResponse = new com.aevi.sdk.nexo.model.PaymentResponse();
        paymentResponse.setPaymentResult(paymentResult(appFlowObject, originalRequest.getPaymentRequest()));
        paymentResponse.setPOIData(poiData(appFlowObject, originalRequest.getPaymentRequest()));
        paymentResponse.setSaleData(saleData(appFlowObject, originalRequest.getPaymentRequest()));
        paymentResponse.setResponse(response("Success")); // TODO later not all payments are successes...
        saleToPOIResponse.setPaymentResponse(paymentResponse);
        return saleToPOIResponse;
    }

    private SaleData saleData(PaymentResponse paymentResponse, PaymentRequest originalRequest) {
        SaleData saleData = new SaleData();
        saleData.setSaleTransactionID(originalRequest.getSaleData().getSaleTransactionID());
        System.out.println("BORTLES: References for sale data are " + paymentResponse.getTransactions().get(0).getPaymentAppResponse().getReferences());
//        saleData.setSaleReferenceID(); // Mandatory if payment reservation or if CustomerOrder is present
        return saleData;
    }

    private POIData poiData(PaymentResponse paymentResponse, PaymentRequest originalRequest) {
        POIData poiData = new POIData();
        TransactionIdentification transactionId = new TransactionIdentification();
        transactionId.setTransactionID(paymentResponse.getTransactions().get(0).getId());
        poiData.setPOITransactionID(transactionId);
//        poiData.setPOIReconciliationID(); // If result is success or partial
        return poiData;
    }

    private PaymentResult paymentResult(PaymentResponse paymentResponse, PaymentRequest originalRequest) {
        PaymentResult result = new PaymentResult();
        result.setAmountsResp(amounts(paymentResponse.getTotalAmountsProcessed()));
        result.setPaymentType(originalRequest.getPaymentData().getPaymentType());
        result.setPaymentInstrumentData(paymentInstrumentData(paymentResponse, originalRequest));

// Only required if supporting digitised signatures
//        result.setCapturedSignature();
//        result.setProtectedSignature();

// If customer language is known and different from default/request language
//        result.setCustomerLanguage();

// Only if payment type is IssuerInstalment
//        result.setInstalment();

// Only if payment forced by cashier
//        result.setMerchantOverrideFlag();

// True default - true if transaction processing happened online
//        result.setOnlineFlag();

// If card analysed and data available
//        result.setPaymentAcquirerData();

// Only for reservations
//        result.setValidityDate();

        return result;
    }

    private PaymentInstrumentData paymentInstrumentData(PaymentResponse paymentResponse, PaymentRequest originalRequest) {
        String paymentMethod = paymentResponse.getTransactions().get(0).getPaymentAppResponse().getPaymentMethod(); // Dangerous?
        PaymentInstrumentType type = null;
        switch (paymentMethod) {
            case PaymentMethods.PAYMENT_METHOD_CARD:
            case PaymentMethods.PAYMENT_METHOD_CREDIT_CARD:
            case PaymentMethods.PAYMENT_METHOD_DEBIT_CARD:
                type = PaymentInstrumentType.CARD;
                break;
            case PaymentMethods.PAYMENT_METHOD_CASH:
                type = PaymentInstrumentType.CASH;
                break;
            case PaymentMethods.PAYMENT_METHOD_CHEQUE:
                type = PaymentInstrumentType.CHECK;
                break;
        }
        PaymentInstrumentData paymentInstrumentData = null;
        if (type != null) {
            paymentInstrumentData = new PaymentInstrumentData();
            paymentInstrumentData.setPaymentInstrumentType(type.value());
            switch (type) {
                case CARD:
                    // TODO copy card data
                    paymentInstrumentData.setCardData(cardData(paymentResponse));
                    break;
                case CHECK:
                    // TODO copy check data
                    break;
            }
        }
        return paymentInstrumentData;
    }

    private CardData cardData(PaymentResponse paymentResponse) {
        TransactionResponse response = paymentResponse.getTransactions().get(0).getPaymentAppResponse(); // Dangerous?
        Card card = response.getCard();

        CardData cardData = new CardData();
        cardData.setMaskedPAN(card.getMaskedPan());
        return cardData;
    }

    private AmountsResp amounts(Amounts amounts) {
        AmountsResp amountsResp = new AmountsResp();
        String currency = amounts.getCurrency();
        amountsResp.setCurrency(currency);
        amountsResp.setAuthorizedAmount(value(amounts.getTotalAmountValue(), currency));
        amountsResp.setCashBackAmount(value(amounts.getAdditionalAmountValue(AmountIdentifiers.AMOUNT_CASHBACK), currency));
        amountsResp.setTipAmount(value(amounts.getAdditionalAmountValue(AmountIdentifiers.AMOUNT_TIP), currency));

// Fees charged from a financial service
//        amountsResp.setTotalFeesAmount();

// Rebates on total amount and/or individual products
//        amountsResp.setTotalRebatesAmount();
        return amountsResp;
    }

    private BigDecimal value(long totalAmountValue, String currency) {
        return null;
    }

    @Override
    public boolean translatesFrom(Object appFlowObject) {
        return appFlowObject instanceof PaymentResponse;
    }
}
