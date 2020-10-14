package com.aevi.sdk.nexo.translators;

import com.aevi.sdk.nexo.model.LoginRequest;
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

    public SaleToPOIResponse encodeAppFlowObject(SaleToPOIRequest request, Object appFlowObject) {
        AppFlowToNexo encoder = getEncoder(appFlowObject);
        SaleToPOIResponse response = encoder == null ? null : (SaleToPOIResponse) encoder.translate(request, appFlowObject);
        return response;
    }

    public NexoToAppFlow getDecoder(SaleToPOIRequest nexoRequest) {
        return SALE_REQUEST_TRANSLATOR;
    }

    /**
     * Decode a Nexo SaleToPOIRequest object to either an AppFlow equivalent model object or an
     * internal model for immediate return (such as a rejected request).
     * <p>Where a login request is provided, this should be used to populate fields where no value
     * is provided in the request, but the data is available from login.</p>
     *
     * @param nexoRequest  The Nexo request to translate
     * @param loginRequest The request with which the sale terminal logged in
     * @return A translated value
     */
    public Object decodeNexoRequest(SaleToPOIRequest nexoRequest, LoginRequest loginRequest) {
        return getDecoder(nexoRequest).translate(nexoRequest, loginRequest);
    }
}
