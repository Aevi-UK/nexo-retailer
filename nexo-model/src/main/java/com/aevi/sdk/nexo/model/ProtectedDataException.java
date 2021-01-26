package com.aevi.sdk.nexo.model;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Exception thrown when an attempt is made to pass protected data in a request.
 */
public class ProtectedDataException extends JsonProcessingException {
    protected ProtectedDataException() {
        super("Attempting to pass in protected data");
    }
}
