package com.vn.model;

import java.math.BigDecimal;

public class KeyValueStringIntegerModel {

    private String key;
    private BigDecimal value;

    public KeyValueStringIntegerModel() {
    }

    public KeyValueStringIntegerModel(String key, BigDecimal value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
