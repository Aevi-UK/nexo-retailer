package com.aevi.sdk.nexo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBuilder;
import com.fasterxml.jackson.databind.deser.BeanDeserializerFactory;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.deser.DeserializerFactory;
import com.fasterxml.jackson.databind.deser.SettableBeanProperty;
import com.fasterxml.jackson.databind.introspect.BasicBeanDescription;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.deser.FromXmlParser;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

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

    public SaleToPOIRequest deserialise(String message, MessageFormat format) {
        switch (format) {
            case XML:
                return deserialiseXML(message);
            case JSON:
                return deserialiseJSON(message);
            default:
                return null;
        }
    }

    public SaleToPOIRequest deserialiseXML(String xml) {
        try {
            ObjectMapper objectMapper = configureXml();

            SaleToPOIRequestType value = objectMapper.readValue(xml, SaleToPOIRequest.class);
            return value == null ? null : new SaleToPOIRequest(value);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return null;
        }
    }

    public SaleToPOIRequest deserialiseJSON(String json) {
        try {
            ObjectMapper objectMapper = configureJson(new JsonMapper());

            SaleToPOIRequestType value = objectMapper.readValue(json, SaleToPOIRequest.class);
            return value == null ? null : new SaleToPOIRequest(value);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return null;
        }
    }

    public SaleToPOIResponseType deserialiseResponseXML(String xml) {
        try {
            ObjectMapper objectMapper = configureXml();

            SaleToPOIResponseType value = objectMapper.readValue(xml, SaleToPOIResponseType.class);
            return value;
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return null;
        }
    }

    public SaleToPOIResponseType deserialiseResponseJSON(String xml) {
        try {
            ObjectMapper objectMapper = configureJson(new JsonMapper());

            SaleToPOIResponse value = objectMapper.readValue(xml, SaleToPOIResponse.class);
            System.err.println("!! " + value);
            return value;
        } catch (JsonProcessingException jpe) {
            System.err.println("BOOM!");
            jpe.printStackTrace();
            return null;
        }
    }

    public String serialiseRequest(Object object) {
        try {
            XmlMapper objectMapper = ((XmlMapper) configureXml())
                    .setDefaultUseWrapper(false);
//            objectMapper.registerModule(new JaxbAnnotationModule());

            String value = objectMapper.writeValueAsString(object);
            return value;
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return null;
        }
    }

    public String serialiseRequestJSON(Object object) {
        try {
            ObjectMapper objectMapper = configureJson(new JsonMapper());

            String value = objectMapper.writeValueAsString(object);
            return value;
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
            return null;
        }
    }

    private static class EntryModeDeserializer extends JsonDeserializer<Object> {
        @Override
        public Object deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            System.err.println("BORTLES: Custom deserialiser");
            List<String> values = new ArrayList();
            values.add("monkeys");
            return values;
        }
    }

    private static class CardDataDeserializerModifier extends BeanDeserializerModifier {
        @Override
        public BeanDeserializerBuilder updateBuilder(DeserializationConfig config, BeanDescription beanDesc, BeanDeserializerBuilder builder) {
            if (beanDesc.getBeanClass() == CardData.class) {
                Iterator<SettableBeanProperty> beanPropertyIterator = builder.getProperties();
                while (beanPropertyIterator.hasNext()) {
                    SettableBeanProperty settableBeanProperty = beanPropertyIterator.next();
                    if ("EntryMode".equals(settableBeanProperty.getName())) {
                        System.err.println("BORTLES: Adding custom deserializer");
                        SettableBeanProperty newSettableBeanProperty = settableBeanProperty.withValueDeserializer(new EntryModeDeserializer());
                        builder.addOrReplaceProperty(newSettableBeanProperty, true);
                        break;
                    }
                }
            }
            return builder;
        }
    }
}
