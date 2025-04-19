package com.unistar.shop.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private String productName;
    private BigDecimal price;
    private Integer quantity;

    @Builder
    public Product(String productName, BigDecimal price, Integer quantity) {
        if (productName == null || productName.equals("")) {
            throw new IllegalArgumentException("productName不可為空字串");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("price 必須大於 0");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity 必須大於 0");
        }
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }
}
