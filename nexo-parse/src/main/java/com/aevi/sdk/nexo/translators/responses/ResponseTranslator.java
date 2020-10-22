package com.aevi.sdk.nexo.translators.responses;

import com.aevi.sdk.nexo.model.MessageHeader;
import com.aevi.sdk.nexo.model.MessageType;
import com.aevi.sdk.nexo.model.Response;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;
import com.aevi.sdk.nexo.model.SaleToPOIResponse;
import com.aevi.sdk.nexo.translators.AppFlowToNexo;

public abstract class ResponseTranslator<T, U extends SaleToPOIResponse> implements AppFlowToNexo<T, U> {
    protected Response successResponse() {
        return response("Success");
    }

    protected Response response(String responseType) {
        Response response = new Response();
        response.setResult(responseType);
        return response;
    }

    protected MessageHeader copyMessageHeader(SaleToPOIRequest request) {
        if (request == null || request.getMessageHeader() == null) {
            return null;
        }

        MessageHeader messageHeader = request.getMessageHeader();
        MessageHeader copy = new MessageHeader();
        copy.setPOIID(messageHeader.getPOIID());
        copy.setSaleID(messageHeader.getSaleID());
        copy.setServiceID(messageHeader.getServiceID());
        copy.setMessageCategory(messageHeader.getMessageCategory());
        copy.setMessageClass(messageHeader.getMessageClass());
        copy.setProtocolVersion(messageHeader.getProtocolVersion());

        copy.setMessageType(MessageType.RESPONSE.value());
        return copy;
    }
}
