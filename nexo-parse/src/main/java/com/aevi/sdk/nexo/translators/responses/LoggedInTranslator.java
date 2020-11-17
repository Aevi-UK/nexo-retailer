package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.responses.LoggedIn;
import com.aevi.sdk.nexo.model.LoginResponse;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class LoggedInTranslator extends ResponseTranslator<LoggedIn, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(SaleToPOIRequest request, LoggedIn appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
        saleToPOIResponse.setMessageHeader(copyMessageHeader(request));
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResponse(successResponse());
        saleToPOIResponse.setLoginResponse(loginResponse);
        return saleToPOIResponse;
    }

    @Override
    public boolean translatesFrom(Object appFlowObject) {
        return appFlowObject instanceof LoggedIn;
    }
}
