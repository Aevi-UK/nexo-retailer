package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.responses.LoginFailure;
import com.aevi.sdk.nexo.model.LoginResponse;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class LoginFailureTranslator extends ResponseTranslator<LoginFailure, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(SaleToPOIRequest request, LoginFailure appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
        saleToPOIResponse.setMessageHeader(copyMessageHeader(request));
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResponse(response("Failure"));
        saleToPOIResponse.setLoginResponse(loginResponse);
        return saleToPOIResponse;
    }

    @Override
    public boolean translatesFrom(Object appFlowObject) {
        return appFlowObject instanceof LoginFailure;
    }
}
