package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.responses.LoginFailure;
import com.aevi.sdk.nexo.model.LoginResponse;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class LoginFailureTranslator extends ResponseTranslator<LoginFailure, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(LoginFailure appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
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
