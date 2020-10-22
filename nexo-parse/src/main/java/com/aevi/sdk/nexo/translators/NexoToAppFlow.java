package com.aevi.sdk.nexo.translators;

import com.aevi.sdk.nexo.model.LoginRequest;

import java.math.BigDecimal;

public interface NexoToAppFlow<T, U> {
    U translate(T nexoObject, LoginRequest loginRequest);

    default boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }
}
