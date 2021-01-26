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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * An intermediate model object for card data that allows us to deserialise entry modes to a string
 * rather than a list, avoiding a Jackson crash on trying to read an attribute as a list.
 */
public class CardData {
        @XmlElement(name = "ProtectedCardData")
        @JsonProperty("ProtectedCardData")
        public ContentInformation protectedCardData;
        @XmlElement(name = "SensitiveCardData")
        @JsonProperty("SensitiveCardData")
        public SensitiveCardData sensitiveCardData;
        @XmlElement(name = "AllowedProductCode")
        @JsonProperty("AllowedProductCode")
        public List<String> allowedProductCode;
        @XmlElement(name = "AllowedProduct")
        @JsonProperty("AllowedProduct")
        public List<AllowedProduct> allowedProduct;
        @XmlElement(name = "PaymentToken")
        @JsonProperty("PaymentToken")
        public PaymentToken paymentToken;
        @XmlElement(name = "CustomerOrder")
        @JsonProperty("CustomerOrder")
        public List<CustomerOrder> customerOrder;
        @XmlAttribute(name = "PaymentBrand")
        @JsonProperty("PaymentBrand")
        @JacksonXmlProperty(isAttribute = true)
        public String paymentBrand;
        @XmlAttribute(name = "MaskedPAN")
        @JsonProperty("MaskedPAN")
        @JacksonXmlProperty(isAttribute = true)
        public String maskedPAN;
        @XmlAttribute(name = "PaymentAccountRef")
        @JsonProperty("PaymentAccountRef")
        @JacksonXmlProperty(isAttribute = true)
        public String paymentAccountRef;
        @XmlAttribute(name = "EntryMode")
        @JsonProperty("EntryMode")
        @JacksonXmlProperty(isAttribute = true)
        public String entryMode;
        @XmlAttribute(name = "CardCountryCode")
        @JsonProperty("CardCountryCode")
        @JacksonXmlProperty(isAttribute = true)
        public String cardCountryCode;
}
