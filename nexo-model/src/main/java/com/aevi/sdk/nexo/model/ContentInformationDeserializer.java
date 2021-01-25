package com.aevi.sdk.nexo.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Deserializer for ContentInformation representing protected data that we should not be handling
 * for certain parent classes.
 */
public class ContentInformationDeserializer extends StdDeserializer<ContentInformation> implements ContextualDeserializer, ResolvableDeserializer {
    /**
     * List of classes which contain ContentInformation that we should fail to read.
     */
    private static final List<Class> FAIL_ON_CONTENT_INFORMATION_IN = Arrays.asList(
            CardData.class,
            CardholderPIN.class,
            MobileData.class
    );
    private JsonDeserializer<?> defaultDeserializer;

    public ContentInformationDeserializer(JsonDeserializer<?> defaultDeserializer) {
        super(ContentInformation.class);
        this.defaultDeserializer = defaultDeserializer;
    }

    @Override
    public ContentInformation deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        throw new ProtectedDataException();
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext deserializationContext, BeanProperty beanProperty) throws JsonMappingException {
        Class<?> parent = beanProperty.getMember().getDeclaringClass();
        if (FAIL_ON_CONTENT_INFORMATION_IN.contains(parent)) {
            return this;
        } else {
            return defaultDeserializer instanceof ContextualDeserializer ? ((ContextualDeserializer) defaultDeserializer).createContextual(deserializationContext, beanProperty) : defaultDeserializer;
        }
    }

    @Override
    public void resolve(DeserializationContext deserializationContext) throws JsonMappingException {
        if (defaultDeserializer instanceof ResolvableDeserializer) {
            ((ResolvableDeserializer) defaultDeserializer).resolve(deserializationContext);
        }
    }
}
