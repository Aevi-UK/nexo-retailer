package com.aevi.sdk.nexo.model;

public class NexoException extends Exception {
    public NexoException() {
        super();
    }

    public NexoException(String s) {
        super(s);
    }

    public NexoException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public NexoException(Throwable throwable) {
        super(throwable);
    }

    protected NexoException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
