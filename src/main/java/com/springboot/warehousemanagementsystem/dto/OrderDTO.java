package com.springboot.warehousemanagementsystem.dto;

import javax.persistence.Transient;

public class OrderDTO {
    @Transient
    private String productName;
    private Long productId;
    private Integer qty;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
