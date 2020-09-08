package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.nexo.extramodel.requests.Admin;
import com.aevi.sdk.nexo.extramodel.requests.Login;
import com.aevi.sdk.nexo.model.AdminRequest;
import com.aevi.sdk.nexo.model.LoginRequest;
import com.aevi.sdk.nexo.translators.NexoToAppFlow;

public class AdminRequestTranslator implements NexoToAppFlow<AdminRequest, Admin> {
    @Override
    public Admin translate(AdminRequest adminRequest) {
        return new Admin();
    }
}
