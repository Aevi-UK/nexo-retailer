package com.aevi.sdk.nexo.translators.requests;

import com.aevi.sdk.nexo.extramodel.RejectedRequest;
import com.aevi.sdk.nexo.model.LoginRequest;
import com.aevi.sdk.nexo.translators.NexoToAppFlow;
import com.aevi.sdk.nexo.model.SaleToPOIRequest;

public class SaleRequestTranslator implements NexoToAppFlow<SaleToPOIRequest, Object> {
    private static final AbortRequestTranslator ABORT_REQUEST_TRANSLATOR = new AbortRequestTranslator();
    private static final PaymentRequestTranslator PAYMENT_REQUEST_TRANSLATOR = new PaymentRequestTranslator();
    private static final LoginRequestTranslator LOGIN_REQUEST_TRANSLATOR = new LoginRequestTranslator();
    private static final LogoutRequestTranslator LOGOUT_REQUEST_TRANSLATOR = new LogoutRequestTranslator();

    @Override
    public Object translate(SaleToPOIRequest nexoObject, LoginRequest loginRequest) {
        RequestType type = RequestType.requestTypeFromSaleRequest(nexoObject);
        switch (type) {
            case PAYMENT:
                return PAYMENT_REQUEST_TRANSLATOR.translate(nexoObject.getPaymentRequest(), loginRequest);
            case ABORT:
                return ABORT_REQUEST_TRANSLATOR.translate(nexoObject.getAbortRequest(), loginRequest);
            case LOGIN:
                return LOGIN_REQUEST_TRANSLATOR.translate(nexoObject.getLoginRequest(), loginRequest);
            case LOGOUT:
                return LOGOUT_REQUEST_TRANSLATOR.translate(nexoObject.getLogoutRequest(), loginRequest);
            default:
                return new RejectedRequest(nexoObject);
        }
        // Not doing anything with message header or security trailer yet...
    }
}
