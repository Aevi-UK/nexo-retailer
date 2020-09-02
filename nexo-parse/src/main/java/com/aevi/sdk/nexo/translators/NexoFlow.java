package com.aevi.sdk.nexo.translators;

import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;
import com.aevi.sdk.nexo.translators.requests.SaleRequestTranslator;
import com.aevi.sdk.nexo.translators.responses.SaleResponseTranslator;

public class NexoFlow {
    private static final SaleRequestTranslator SALE_REQUEST_TRANSLATOR = new SaleRequestTranslator();
    private static final SaleResponseTranslator SALE_RESPONSE_TRANSLATOR = new SaleResponseTranslator();

    public AppFlowToNexo getEncoder(Object appFlowObject) {
        return SALE_RESPONSE_TRANSLATOR;
    }

    public SaleToPOIResponse encodeAppFlowObject(Object appFlowObject) {
        AppFlowToNexo encoder = getEncoder(appFlowObject);
        SaleToPOIResponse response = encoder == null ? null : (SaleToPOIResponse) encoder.translate(appFlowObject);
        return response;
    }

    public NexoToAppFlow getDecoder(SaleToPOIRequest nexoRequest) {
        return SALE_REQUEST_TRANSLATOR;
    }

    public Object decodeNexoRequest(SaleToPOIRequest nexoRequest) {
        return getDecoder(nexoRequest).translate(nexoRequest);
    }
}
