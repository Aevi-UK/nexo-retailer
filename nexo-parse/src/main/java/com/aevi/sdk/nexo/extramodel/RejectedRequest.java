package com.aevi.sdk.nexo.extramodel;

import com.aevi.sdk.nexo.model.ErrorCondition;
import com.aevi.sdk.nexo.model.Response;
import com.aevi.sdk.nexo.model.ResponseHolder;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;

public class RejectedRequest {
    private SaleToPOIRequest request;

    private ErrorCondition errorCondition;

    public RejectedRequest(SaleToPOIRequest request) {
        this(request, ErrorCondition.NOT_ALLOWED);
    }

    public RejectedRequest(SaleToPOIRequest request, ErrorCondition errorCondition) {
        this.request = request;
        this.errorCondition = errorCondition;
    }

    public SaleToPOIResponse generateResponse() {
        SaleToPOIResponse response = null;
        ResponseHolder responseHolder = request.createResponseHolder();
        if (responseHolder != null) {
            response = new SaleToPOIResponse();
            responseHolder.setResponse(createResponse());
            response.setResponse(responseHolder);
        } else if (request.getAbortRequest() != null) {
            // Aborts don't have direct responses...
        } else if (request.getInputRequest() != null) {
            // Input has wacky multiple responses
        } else if (request.getDisplayRequest() != null) {
            // Display has wacky nested responses
        }
        if (response == null) {
            System.err.println("Rejected request: no response holder generated for " + request);
        }
        return response;
    }

    private Response createResponse() {
        Response response = new Response();
        response.setResult("Failure");
        response.setErrorCondition(errorCondition.value());
        return response;
    }
}
