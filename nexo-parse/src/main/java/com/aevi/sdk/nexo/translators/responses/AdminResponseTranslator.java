package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.extramodel.responses.AdminResponse;
import com.aevi.sdk.nexo.extramodel.responses.LoggedIn;
import com.aevi.sdk.nexo.model.LoginResponse;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class AdminResponseTranslator extends ResponseTranslator<AdminResponse, SaleToPOIResponse> {
    @Override
    public SaleToPOIResponse translate(SaleToPOIRequest request, AdminResponse appFlowObject) {
        SaleToPOIResponse saleToPOIResponse = new SaleToPOIResponse();
        saleToPOIResponse.setMessageHeader(copyMessageHeader(request));
        com.aevi.sdk.nexo.model.AdminResponse adminResponse = new com.aevi.sdk.nexo.model.AdminResponse();
        adminResponse.setResponse(successResponse());
        saleToPOIResponse.setAdminResponse(adminResponse);
        return saleToPOIResponse;
    }

    @Override
    public boolean translatesFrom(Object appFlowObject) {
        return appFlowObject instanceof LoggedIn;
    }
}
