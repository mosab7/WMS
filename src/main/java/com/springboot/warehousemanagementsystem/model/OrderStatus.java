package com.springboot.warehousemanagementsystem.model;

public enum OrderStatus {
    SHIPPED("Shipped"),
    CONFIRMED("Ready to Ship"),

    NOT_ENOUGH_PRODUCT("Not Enough Stock Items"),
    NOT_APPLICABLE("Not Applicable");

    private final String value;

    private OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
