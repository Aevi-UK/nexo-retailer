package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.responses.LogoutFailure;
import com.aevi.sdk.nexo.model.LogoutResponse;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class LogoutFailureTranslator extends ResponseTranslator<LogoutFailure, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(LogoutFailure appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
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
