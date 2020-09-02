package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.model.Response;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;
import com.aevi.sdk.nexo.translators.AppFlowToNexo;

public abstract class ResponseTranslator<T, U extends SaleToPOIResponse> implements AppFlowToNexo<T, U> {
    protected Response successResponse() {
        return response("Success");
    }

    protected Response response(String responseType) {
        Response response = new Response();
        response.setResult(responseType);
        return response;
    }
}
