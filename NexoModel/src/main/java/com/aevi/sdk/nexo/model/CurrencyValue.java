package com.aevi.sdk.nexo.model;

import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Currency;

public class CurrencyValue {
    private BigDecimal asBigDecimal;
    private Long asLong;
    private String currency;

    public CurrencyValue(@NonNull BigDecimal amount) {
        this.asBigDecimal = amount;
    }

    public CurrencyValue(long amount) {
        this.asLong = amount;
    }

    public CurrencyValue(@NonNull BigDecimal amount, String currency) {
        this(amount);
        this.currency = currency;
    }

    public CurrencyValue(long amount, String currency) {
        this(amount);
        this.currency = currency;
    }

    public BigDecimal getAsBigDecimal() {
        return asBigDecimal;
    }

    public BigDecimal getAsBigDecimal(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        int subUnitFraction = currency.getDefaultFractionDigits();
        return BigDecimal.valueOf(asLong).movePointLeft(subUnitFraction);
    }
}
