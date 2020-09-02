package com.aevi.sdk.nexo.translators;

public interface AppFlowToNexo<T, U> {
    U translate(T appFlowObject);

    default boolean translatesFrom(Object appFlowObject) {
        return false;
    }
}
