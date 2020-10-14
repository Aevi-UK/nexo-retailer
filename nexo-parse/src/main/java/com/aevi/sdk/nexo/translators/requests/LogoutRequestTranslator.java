package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.nexo.extramodel.requests.Logout;
import com.aevi.sdk.nexo.model.LoginRequest;
import com.aevi.sdk.nexo.model.LogoutRequest;
import com.aevi.sdk.nexo.translators.NexoToAppFlow;

public class LogoutRequestTranslator implements NexoToAppFlow<LogoutRequest, Logout> {
    @Override
    public Logout translate(LogoutRequest logoutRequest, LoginRequest loginRequest) {
        return new Logout();
    }
}
