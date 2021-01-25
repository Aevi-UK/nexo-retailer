package com.aevi.sdk.nexo.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The CardData class is defined by the Nexo spec as having a list of values in the entry mode
 * property that is represented as an attribute. This leads to a crash in Jackson's XML parser, as
 * it expects any lists to be a set of wrapped elements, and when customising deserialisation of
 * the single property it still crashes while attempting to read the data from that attribute.
 * <p>The workaround used here is to deserialise to a separate model class with a simple String
 * value in place of the List&lt;String&gt; from the spec, then copy the data across to a new
 * instance of CardData.</p>
 * <p>Although the examples only list a single entry mode, as the spec declares multiple, we
 * split the attribute by whitespace.</p>
 */
public class CardDataDeserializer extends StdDeserializer<CardData> {
    protected CardDataDeserializer() {
        super(CardData.class);
    }

    @Override
    public CardData deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        com.aevi.sdk.nexo.model.intermediate.CardData intermediateCardData = deserializationContext.readValue(jsonParser, com.aevi.sdk.nexo.model.intermediate.CardData.class);
        CardData cardData = new CardData();
        if (intermediateCardData != null) {
            cardData.allowedProduct = intermediateCardData.allowedProduct;
            cardData.allowedProductCode = intermediateCardData.allowedProductCode;
            cardData.cardCountryCode = intermediateCardData.cardCountryCode;
            cardData.customerOrder = intermediateCardData.customerOrder;
            cardData.entryMode = processEntryMode(intermediateCardData.entryMode);
            cardData.maskedPAN = intermediateCardData.maskedPAN;
            cardData.paymentAccountRef = intermediateCardData.paymentAccountRef;
            cardData.paymentBrand = intermediateCardData.paymentBrand;
            cardData.paymentToken = intermediateCardData.paymentToken;
            cardData.protectedCardData = intermediateCardData.protectedCardData;
            cardData.sensitiveCardData = intermediateCardData.sensitiveCardData;
        }
        return cardData;
    }

    private List<String> processEntryMode(String entryMode) {
        if (entryMode == null) {
            return null;
        } else {
            return Arrays.asList(entryMode.trim().split("\\s+"));
        }
    }
}
