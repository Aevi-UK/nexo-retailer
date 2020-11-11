package com.aevi.sdk.nexo.model;

public class InvalidMessageException extends NexoException {
    public InvalidMessageException() {
        super("Failed to parse Nexo message");
    }

    public InvalidMessageException(String s) {
        super(s);
    }

    public InvalidMessageException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public InvalidMessageException(Throwable throwable) {
        super(throwable);
    }

    protected InvalidMessageException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
