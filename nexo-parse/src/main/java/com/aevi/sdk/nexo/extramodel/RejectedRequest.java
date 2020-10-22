package com.aevi.sdk.nexo.extramodel;

import com.aevi.sdk.nexo.model.DisplayOutput;
import com.aevi.sdk.nexo.model.DisplayResponse;
import com.aevi.sdk.nexo.model.ErrorCondition;
import com.aevi.sdk.nexo.model.InputData;
import com.aevi.sdk.nexo.model.InputResponse;
import com.aevi.sdk.nexo.model.InputResult;
import com.aevi.sdk.nexo.model.OutputResult;
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
            response = new SaleToPOIResponse();
            InputResponse inputResponse = new InputResponse();
            inputResponse.setOutputResult(outputResultFromDisplayOutput(request.getInputRequest().getDisplayOutput()));
            inputResponse.setInputResult(inputResultFromInputData(request.getInputRequest().getInputData()));
        } else if (request.getDisplayRequest() != null) {
            response = new SaleToPOIResponse();
            DisplayResponse displayResponse = new DisplayResponse();
            for (DisplayOutput displayOutput : request.getDisplayRequest().getDisplayOutput()) {
                displayResponse.getOutputResult().add(outputResultFromDisplayOutput(displayOutput));
            }
            response.setDisplayResponse(displayResponse);
        }
        if (response == null) {
            System.err.println("Rejected request: no response holder generated for " + request);
        }
        return response;
    }

    private OutputResult outputResultFromDisplayOutput(DisplayOutput displayOutput) {
        OutputResult result = new OutputResult();
        result.setDevice(displayOutput.getDevice());
        result.setInfoQualify(displayOutput.getInfoQualify());
        result.setResponse(createResponse());
        return result;
    }

    private InputResult inputResultFromInputData(InputData inputData) {
        InputResult result = new InputResult();
        result.setDevice(inputData.getDevice());
        result.setInfoQualify(inputData.getInfoQualify());
        result.setResponse(createResponse());
        return result;
    }

    private Response createResponse() {
        Response response = new Response();
        response.setResult("Failure");
        response.setErrorCondition(errorCondition.value());
        return response;
    }
}
