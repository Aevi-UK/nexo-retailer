package com.aevi.sdk.nexo.model.intermediate;

import com.aevi.sdk.nexo.model.AllowedProduct;
import com.aevi.sdk.nexo.model.ContentInformation;
import com.aevi.sdk.nexo.model.CustomerOrder;
import com.aevi.sdk.nexo.model.PaymentToken;
import com.aevi.sdk.nexo.model.SensitiveCardData;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * An intermediate model object for card data that allows us to deserialise entry modes to a string
 * rather than a list, avoiding a Jackson crash on trying to read an attribute as a list.
 */
public class CardData {
        @JsonProperty("ProtectedCardData")
        public ContentInformation protectedCardData;
        @JsonProperty("SensitiveCardData")
        public SensitiveCardData sensitiveCardData;
        @JsonProperty("AllowedProductCode")
        public List<String> allowedProductCode;
        @JsonProperty("AllowedProduct")
        public List<AllowedProduct> allowedProduct;
        @JsonProperty("PaymentToken")
        public PaymentToken paymentToken;
        @JsonProperty("CustomerOrder")
        public List<CustomerOrder> customerOrder;
        @JsonProperty("PaymentBrand")
        @JacksonXmlProperty(isAttribute = true)
        public String paymentBrand;
        @JsonProperty("MaskedPAN")
        @JacksonXmlProperty(isAttribute = true)
        public String maskedPAN;
        @JsonProperty("PaymentAccountRef")
        @JacksonXmlProperty(isAttribute = true)
        public String paymentAccountRef;
        @JsonProperty("EntryMode")
        @JacksonXmlProperty(isAttribute = true)
        public String entryMode;
        @JsonProperty("CardCountryCode")
        @JacksonXmlProperty(isAttribute = true)
        public String cardCountryCode;
}
