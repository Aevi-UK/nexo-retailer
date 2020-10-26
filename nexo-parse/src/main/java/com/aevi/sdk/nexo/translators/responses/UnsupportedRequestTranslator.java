package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.RejectedRequest;
import com.aevi.sdk.nexo.extramodel.responses.LoggedIn;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class UnsupportedRequestTranslator extends ResponseTranslator<RejectedRequest, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(SaleToPOIRequest originalRequest, RejectedRequest rejectedRequest) {
        SaleToPOIResponse response = rejectedRequest.generateResponse();
        if (response != null) {
            response.setMessageHeader(copyMessageHeader(originalRequest));
        }
        return response;
    }


    @Override
    public boolean translatesFrom(Object appFlowObject) {
        return appFlowObject instanceof RejectedRequest;
    }
}
