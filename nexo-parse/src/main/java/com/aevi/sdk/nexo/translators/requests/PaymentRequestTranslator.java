package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.flow.constants.AmountIdentifiers;
import com.aevi.sdk.flow.constants.FlowTypes;
import com.aevi.sdk.nexo.model.PaymentType;
import com.aevi.sdk.nexo.model.SaleData;
import com.aevi.sdk.nexo.translators.NexoToAppFlow;
import com.aevi.sdk.nexo.model.AmountsReq;
import com.aevi.sdk.nexo.model.PaymentRequest;
import com.aevi.sdk.nexo.model.PaymentTransaction;
import com.aevi.sdk.nexo.model.SaleItem;
import com.aevi.sdk.pos.flow.model.Amounts;
import com.aevi.sdk.pos.flow.model.Basket;
import com.aevi.sdk.pos.flow.model.BasketItem;
import com.aevi.sdk.pos.flow.model.BasketItemBuilder;
import com.aevi.sdk.pos.flow.model.Payment;
import com.aevi.sdk.pos.flow.model.PaymentBuilder;

import java.math.BigDecimal;
import java.util.List;

public class PaymentRequestTranslator implements NexoToAppFlow<PaymentRequest, Payment> {
    @Override
    public Payment translate(PaymentRequest paymentRequest) {
        PaymentBuilder builder = new PaymentBuilder();
        if (paymentRequest.getSaleData() != null) {
            // Should always be present
            SaleData saleData = paymentRequest.getSaleData();
        }
        if (paymentRequest.getPaymentTransaction() != null) {
            // Should always be present
            PaymentTransaction paymentTransaction = paymentRequest.getPaymentTransaction();
            builder.withAmounts(amounts(paymentTransaction.getAmountsReq()));
        }
        if (paymentRequest.getPaymentData() != null) {
            builder.withSplitEnabled(paymentRequest.getPaymentData().isSplitPaymentFlag());
        }
        builder.withPaymentFlow(flowType(paymentRequest));
        return builder.build();
    }

    private String flowType(PaymentRequest paymentRequest) {
        String paymentTypeValue = paymentRequest.getPaymentData() == null ? null : paymentRequest.getPaymentData().getPaymentType();
        PaymentType paymentType = PaymentType.NORMAL;
        if (paymentTypeValue != null) {
            paymentType = PaymentType.fromValue(paymentTypeValue);
        }
        switch (paymentType) {
            case NORMAL:
                return FlowTypes.FLOW_TYPE_SALE;
            case REFUND:
                return FlowTypes.FLOW_TYPE_REFUND;
            default:
                return null;
        }
    }

    private Amounts amounts(AmountsReq amountsReq) {
        Amounts amounts = new Amounts(amountsReq.getRequestedAmount().multiply(BigDecimal.valueOf(100)).longValue(), amountsReq.getCurrency());
        if (amountsReq.getTipAmount() != null) {
            amounts.addAdditionalAmount(AmountIdentifiers.AMOUNT_TIP, amountsReq.getTipAmount().multiply(BigDecimal.valueOf(100)).longValue());
        }
        if (amountsReq.getCashBackAmount() != null) {
            amounts.addAdditionalAmount(AmountIdentifiers.AMOUNT_CASHBACK, amountsReq.getCashBackAmount().multiply(BigDecimal.valueOf(100)).longValue());
        }
        return amounts;
    }
}
