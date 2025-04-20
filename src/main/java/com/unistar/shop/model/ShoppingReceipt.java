package com.unistar.shop.model;

import com.unistar.shop.config.ProductCategoryConfig;
import com.unistar.shop.enums.Location;
import com.unistar.shop.enums.ProductCategory;
import lombok.Builder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


public class ShoppingReceipt {
    private List<Product> products;
    private Location location;

    @Builder
    public ShoppingReceipt(List<Product> products, Location location) {
        if (products == null || products.size() == 0) {
            throw new IllegalArgumentException("products不可為空");
        }
        if (location == null) {
            throw new IllegalArgumentException("location不可為空");
        }
        this.products = products;
        this.location = location;
    }

    /**
     * 印出票據
     */
    public void printReceipt() {
        // 票據內容
//        StringBuilder receipt = new StringBuilder();

        // 列印標題
        System.out.println(this.getReceiptRow("item", "price", "qty"));
        System.out.println();

        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;
        for (Product product : products) {
            String productName = product.getProductName();
            BigDecimal price = product.getPrice();
            Integer quantity = product.getQuantity();

            // 商品費用 productFee
            BigDecimal productFee = price.multiply(BigDecimal.valueOf(quantity));
            subTotal = subTotal.add(productFee);

            // 商品稅 productTax
            ProductCategory productCategory = ProductCategoryConfig.getCategory(productName);
            BigDecimal productTax = this.getProductTax(location, productCategory, productFee);
            tax = tax.add(productTax);

            // 列印品項
            String priceString = this.getPriceString(price);
            String quantityString = String.format("%d", quantity);
            System.out.println(this.getReceiptRow(productName, priceString, quantityString));
        }
        // 總費用
        BigDecimal total = subTotal.add(tax);

        //列印總費用
        String subTotalString = this.getPriceString(subTotal);
        System.out.println(this.getReceiptRow("subtotal:", "", subTotalString));

        String taxString = this.getPriceString(tax);
        System.out.println(this.getReceiptRow("tax:", "", taxString));

        String totalString = this.getPriceString(total);
        System.out.println(this.getReceiptRow("total:", "", totalString));

        // 打印結果
//        System.out.println(receipt);
    }

    /**
     * 印出票據(列)
     */
    private String getReceiptRow(String arg1, String arg2, String arg3) {
        return String.format("%-16s %10s %10s", arg1, arg2, arg3);
    }

    /**
     * 獲取 $價格
     */
    private String getPriceString(BigDecimal amount) {
        return String.format("$%.2f", amount);
    }

    /**
     * 計算商品稅
     */
    private BigDecimal getProductTax(Location location, ProductCategory productCategory, BigDecimal productFee) {
        BigDecimal salesTaxRate = location.getSalesTaxRate(productCategory);
        BigDecimal productTax = salesTaxRate.multiply(productFee);
        return this.roundUpToNearest005(productTax);
    }

    /**
     * 四捨五入到最接近的 0.05（進位）
     * 例如：1.13 -> 1.15, 1.16 -> 1.20
     */
    private BigDecimal roundUpToNearest005(BigDecimal amount) {
        double v = Math.ceil(amount.doubleValue() * 20) / 20;
        return new BigDecimal(v).setScale(2, RoundingMode.HALF_UP);
    }
}
