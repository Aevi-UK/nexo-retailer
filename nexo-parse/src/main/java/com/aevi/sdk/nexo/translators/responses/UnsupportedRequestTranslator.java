package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.RejectedRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class UnsupportedRequestTranslator extends ResponseTranslator<RejectedRequest, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(RejectedRequest rejectedRequest) {
        return rejectedRequest.generateResponse();
    }
}
