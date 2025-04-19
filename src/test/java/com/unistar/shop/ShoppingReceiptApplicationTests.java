package com.unistar.shop;

import com.unistar.shop.enums.Location;
import com.unistar.shop.model.Product;
import com.unistar.shop.model.ShoppingReceipt;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class ShoppingReceiptApplicationTests {
    @Test
    void useCase1() {
        // 產生收據
        Location location = Location.CA;
        List<Product> products = Lists.newArrayList(
                Product.builder()
                       .productName("book")
                       .price(BigDecimal.valueOf(17.99))
                       .quantity(1)
                       .build(),
                Product.builder()
                       .productName("potato chips")
                       .price(BigDecimal.valueOf(3.99))
                       .quantity(1)
                       .build()
        );
        ShoppingReceipt shoppingReceipt = ShoppingReceipt.builder()
                                                         .products(products)
                                                         .location(location)
                                                         .build();
        // 列印收據
        shoppingReceipt.printReceipt();
    }

    @Test
    void useCase2() {
        // 產生收據
        Location location = Location.NY;
        List<Product> products = Lists.newArrayList(
                Product.builder()
                       .productName("book")
                       .price(BigDecimal.valueOf(17.99))
                       .quantity(1)
                       .build(),
                Product.builder()
                       .productName("pencil")
                       .price(BigDecimal.valueOf(2.99))
                       .quantity(3)
                       .build()
        );
        ShoppingReceipt shoppingReceipt = ShoppingReceipt.builder()
                                                         .products(products)
                                                         .location(location)
                                                         .build();
        // 列印收據
        shoppingReceipt.printReceipt();
    }

    @Test
    void useCase3() {
        // 產生收據
        Location location = Location.NY;
        List<Product> products = Lists.newArrayList(
                Product.builder()
                       .productName("pencil")
                       .price(BigDecimal.valueOf(2.99))
                       .quantity(2)
                       .build(),
                Product.builder()
                       .productName("shirt")
                       .price(BigDecimal.valueOf(29.99))
                       .quantity(1)
                       .build()
        );
        ShoppingReceipt shoppingReceipt = ShoppingReceipt.builder()
                                                         .products(products)
                                                         .location(location)
                                                         .build();
        // 列印收據
        shoppingReceipt.printReceipt();
    }
}