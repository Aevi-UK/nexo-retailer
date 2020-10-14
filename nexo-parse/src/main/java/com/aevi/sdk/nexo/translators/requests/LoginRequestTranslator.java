package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.nexo.extramodel.requests.Login;
import com.aevi.sdk.nexo.model.LoginRequest;
import com.aevi.sdk.nexo.translators.NexoToAppFlow;

public class LoginRequestTranslator implements NexoToAppFlow<LoginRequest, Login> {
    @Override
    public Login translate(LoginRequest loginRequest, LoginRequest existingLogin) {
        // New login request should supersede the existing one
        return new Login(loginRequest);
    }
}
