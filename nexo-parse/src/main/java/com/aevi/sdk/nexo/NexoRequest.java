package com.aevi.sdk.nexo;

import com.aevi.sdk.flow.model.FlowEvent;
import com.aevi.sdk.flow.model.Request;
import com.aevi.sdk.nexo.extramodel.NexoModel;
import com.aevi.sdk.pos.flow.model.Payment;

public abstract class NexoRequest {
    private Object request;

    public NexoRequest(Object request) {
        this.request = request;
    }

    public boolean isAppFlowPayment() {
        return request instanceof Payment;
    }

    public boolean isNexoModel() {
        return request instanceof NexoModel;
    }

    public boolean isAppFlowRequest() {
        return request instanceof Request;
    }

    public boolean isAppFlowEvent() {
        return request instanceof FlowEvent;
    }

    public Payment getAsAppFlowPayment() {
        return isAppFlowPayment() ? (Payment) request : null;
    }

    public NexoModel getAsNexoModel() {
        return isNexoModel() ? (NexoModel) request : null;
    }

    public Request getAsAppFlowRequest() {
        return isAppFlowRequest() ? (Request) request : null;
    }

    public FlowEvent getAsAppFlowEvent() {
        return isAppFlowEvent() ? (FlowEvent) request : null;
    }

    public Object getRaw() {
        return request;
    }

    public abstract void sendResponse(Object response);
}
