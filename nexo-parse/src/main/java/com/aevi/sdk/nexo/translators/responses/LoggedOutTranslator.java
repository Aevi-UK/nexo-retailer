package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.responses.LoggedOut;
import com.aevi.sdk.nexo.model.LogoutResponse;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class LoggedOutTranslator extends ResponseTranslator<LoggedOut, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(LoggedOut appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setResponse(successResponse());
        saleToPOIResponse.setLogoutResponse(logoutResponse);
        return saleToPOIResponse;
    }

    @Override
    public boolean translatesFrom(Object appFlowObject) {
        return appFlowObject instanceof LoggedOut;
    }
}
