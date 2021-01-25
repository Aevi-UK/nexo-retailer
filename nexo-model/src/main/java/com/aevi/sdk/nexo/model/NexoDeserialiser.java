package com.aevi.sdk.nexo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class NexoDeserialiser {
    private ObjectMapper configureJson(ObjectMapper objectMapper) {
        return configure(objectMapper)
                .configure(SerializationFeature.WRAP_ROOT_VALUE, true)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
    }

    private ObjectMapper configureXml() {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);

        // Custom deserialization of CardData is required to prevent crashes from attempting to
        // deserialize an attribute as a list.
        module.addDeserializer(CardData.class, new CardDataDeserializer());

        // Add a modifier to deserialise ContentInformation with our ProtectedDataDeserialiser.
        // Note that we can't simply add it as a deserializer, as we need access to the default
        // deserializer for classes that can safely contain protected data.
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                JsonDeserializer<?> configuredDeserializer = super.modifyDeserializer(config, beanDesc, deserializer);
                if (ContentInformation.class.isAssignableFrom(beanDesc.getBeanClass())) {
                    configuredDeserializer = new ContentInformationDeserializer(configuredDeserializer);
                }

                return configuredDeserializer;
            }
        });

        module.addDeserializer(SensitiveMobileData.class, new ProtectedDataDeserializer(SensitiveMobileData.class));
        module.addDeserializer(SensitiveCardData.class, new ProtectedDataDeserializer(SensitiveCardData.class));

        XmlMapper mapper = new XmlMapper(module);
        return configure(mapper.disable(FromXmlParser.Feature.EMPTY_ELEMENT_AS_NULL));
    }

    private ObjectMapper configure(ObjectMapper objectMapper) {
        objectMapper = objectMapper
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE)
                .registerModule(new JavaTimeModule());
        objectMapper = objectMapper
                .setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                        .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                        .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        return objectMapper;
    }

    public SaleToPOIRequest deserialise(String message, MessageFormat format) throws NexoException {
        switch (format) {
            case XML:
                return deserialiseXML(message);
            case JSON:
                return deserialiseJSON(message);
            default:
                return null;
        }
    }

    public SaleToPOIRequest deserialiseXML(String xml) throws NexoException {
        try {
            ObjectMapper objectMapper = configureXml();

            SaleToPOIRequestType value = objectMapper.readValue(xml, SaleToPOIRequest.class);
            return value == null ? null : new SaleToPOIRequest(value);
        } catch (JsonProcessingException jpe) {
            throw mapAsNexoException(jpe);
        }
    }

    public SaleToPOIRequest deserialiseJSON(String json) throws NexoException {
        try {
            ObjectMapper objectMapper = configureJson(new JsonMapper());

            SaleToPOIRequestType value = objectMapper.readValue(json, SaleToPOIRequest.class);
            return value == null ? null : new SaleToPOIRequest(value);
        } catch (JsonProcessingException jpe) {
            throw mapAsNexoException(jpe);
        }
    }

    public SaleToPOIResponseType deserialiseResponseXML(String xml) throws NexoException {
        try {
            ObjectMapper objectMapper = configureXml();

            SaleToPOIResponseType value = objectMapper.readValue(xml, SaleToPOIResponseType.class);
            return value;
        } catch (JsonProcessingException jpe) {
            throw mapAsNexoException(jpe);
        }
    }

    public SaleToPOIResponseType deserialiseResponseJSON(String xml) throws NexoException {
        try {
            ObjectMapper objectMapper = configureJson(new JsonMapper());

            SaleToPOIResponse value = objectMapper.readValue(xml, SaleToPOIResponse.class);
            System.err.println("!! " + value);
            return value;
        } catch (JsonProcessingException jpe) {
            throw mapAsNexoException(jpe);
        }
    }

    public String serialiseRequest(Object object) throws NexoException {
        try {
            XmlMapper objectMapper = ((XmlMapper) configureXml())
                    .setDefaultUseWrapper(false);

            String value = objectMapper.writeValueAsString(object);
            return value;
        } catch (JsonProcessingException jpe) {
            throw mapAsNexoException(jpe);
        }
    }

    public String serialiseRequestJSON(Object object) throws NexoException {
        try {
            ObjectMapper objectMapper = configureJson(new JsonMapper());

            String value = objectMapper.writeValueAsString(object);
            return value;
        } catch (JsonProcessingException jpe) {
            throw mapAsNexoException(jpe);
        }
    }

    private NexoException mapAsNexoException(JsonProcessingException jpe) {
        if (jpe instanceof ProtectedDataException || jpe.getCause() instanceof ProtectedDataException) {
            return new ProtectedInformationException();
        } else {
            return new InvalidMessageException(jpe);
        }
    }
}
