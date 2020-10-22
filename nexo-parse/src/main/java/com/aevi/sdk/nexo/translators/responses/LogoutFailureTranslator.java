package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.responses.LogoutFailure;
import com.aevi.sdk.nexo.model.LogoutResponse;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class LogoutFailureTranslator extends ResponseTranslator<LogoutFailure, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(SaleToPOIRequest request, LogoutFailure appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
        saleToPOIResponse.setMessageHeader(copyMessageHeader(request));
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setResponse(response("Failure"));
        saleToPOIResponse.setLogoutResponse(logoutResponse);
        return saleToPOIResponse;
    }

    @Override
    public boolean translatesFrom(Object appFlowObject) {
        return appFlowObject instanceof LogoutFailure;
    }
}
