package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.flow.constants.events.FlowEventTypes;
import com.aevi.sdk.flow.constants.events.NotifyActionTypes;
import com.aevi.sdk.flow.model.FlowEvent;
import com.aevi.sdk.nexo.model.AbortRequest;
import com.aevi.sdk.nexo.model.LoginRequest;
import com.aevi.sdk.nexo.translators.NexoToAppFlow;
import com.aevi.sdk.pos.flow.model.events.NotifyAction;

public class AbortRequestTranslator implements NexoToAppFlow<AbortRequest, FlowEvent> {
    @Override
    public FlowEvent translate(AbortRequest nexoObject, LoginRequest loginRequest) {
        NotifyAction notifyAction = new NotifyAction(NotifyActionTypes.ABORT);
        return new FlowEvent(FlowEventTypes.EVENT_NOTIFY_ACTION, notifyAction);
    }
}
