package com.aevi.sdk.nexo.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ProtectedDataDeserializer<T> extends StdDeserializer<T> {
    public ProtectedDataDeserializer(Class<T> protectedClass) {
        super(protectedClass);
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        throw new ProtectedDataException();
    }
}
