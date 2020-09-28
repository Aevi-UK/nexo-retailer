package com.aevi.sdk.nexo.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

import org.eclipse.persistence.jaxb.xmlmodel.XmlMap;

public class NexoDeserialiser {
    private ObjectMapper configureJson(ObjectMapper objectMapper) {
        return configure(objectMapper)
                .configure(SerializationFeature.WRAP_ROOT_VALUE, true)
                .configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);
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

    public SaleToPOIRequest deserialiseXML(String xml) {
        try {
            ObjectMapper objectMapper = configure(new XmlMapper());

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
            ObjectMapper objectMapper = configure(new XmlMapper());

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

            SaleToPOIResponseType value = objectMapper.readValue(xml, SaleToPOIResponseType.class);
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
            XmlMapper objectMapper = ((XmlMapper) configure(new XmlMapper()))
                    .setDefaultUseWrapper(false);
            objectMapper.registerModule(new JaxbAnnotationModule());

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
}