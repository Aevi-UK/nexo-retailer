package com.aevi.sdk.nexo.extramodel.requests;

import com.aevi.sdk.nexo.extramodel.LoginNotRequired;
import com.aevi.sdk.nexo.extramodel.NexoModel;
import com.aevi.sdk.nexo.model.LoginRequest;

public class Login extends NexoModel implements LoginNotRequired {
    LoginRequest loginRequest;

    public Login(LoginRequest loginRequest) {
        this.loginRequest = loginRequest;
    }

    public LoginRequest getLoginRequest() {
        return loginRequest;
    }
}
