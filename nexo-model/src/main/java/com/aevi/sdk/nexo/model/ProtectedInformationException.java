package com.aevi.sdk.nexo.model;

public class ProtectedInformationException extends NexoException {
    public ProtectedInformationException() {
        super("Message contained unsupported confidential data");
    }

    public ProtectedInformationException(String s) {
        super(s);
    }

    public ProtectedInformationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ProtectedInformationException(Throwable throwable) {
        super(throwable);
    }

    protected ProtectedInformationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
