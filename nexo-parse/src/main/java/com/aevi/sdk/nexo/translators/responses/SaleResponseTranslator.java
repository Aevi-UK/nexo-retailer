package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;
import com.aevi.sdk.nexo.translators.AppFlowToNexo;

import java.util.Arrays;
import java.util.List;

public class SaleResponseTranslator extends ResponseTranslator<Object, SaleToPOIResponse> {
    private static final List<AppFlowToNexo> TRANSLATORS = Arrays.asList(
            new UnsupportedRequestTranslator(),
            new PaymentResponseTranslator(),
            new LoggedInTranslator(),
            new LoginFailureTranslator(),
            new LoggedOutTranslator(),
            new LogoutFailureTranslator());

    @Override
    public SaleToPOIResponse translate(SaleToPOIRequest originalRequest, Object appFlowObject) {
        for (AppFlowToNexo translator : TRANSLATORS) {
            if (translator.translatesFrom(appFlowObject)) {
                Object translated = translator.translate(originalRequest, appFlowObject);
                if (translated instanceof SaleToPOIResponse) {
                    return (SaleToPOIResponse) translated;
                }
            }
        }
        return null;
    }
}