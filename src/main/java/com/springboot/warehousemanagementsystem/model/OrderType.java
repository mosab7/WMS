package com.springboot.warehousemanagementsystem.model;

public enum OrderType {
    INBOUND("Inbound"),
    OUTBOUND("Outbound");

    private final String value;

    private OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

