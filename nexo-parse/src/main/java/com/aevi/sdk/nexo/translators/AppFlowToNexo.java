package com.aevi.sdk.nexo.translators;

import com.aevi.sdk.nexo.model.SaleToPOIRequest;

public interface AppFlowToNexo<T, U> {
    U translate(SaleToPOIRequest request, T appFlowObject);

    default boolean translatesFrom(Object appFlowObject) {
        return false;
    }
}
