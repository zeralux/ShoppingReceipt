package com.unistar.shop.config;

import com.unistar.shop.enums.ProductCategory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProductCategoryConfig {

    private static final String YAML_PATH = "/product-category.yml";
    private static final Map<String, ProductCategory> categoryMap = new HashMap<>();

    private ProductCategoryConfig() {
    }

    // 啟動時讀取
    static {
        Yaml yaml = new Yaml();
        InputStream inputStream = ProductCategoryConfig.class.getResourceAsStream(YAML_PATH);
        if (inputStream == null) {
            throw new RuntimeException("讀取YAML檔錯誤: " + YAML_PATH + " 不存在");
        }

        Map<String, String> rawMap = yaml.load(inputStream);
        for (Map.Entry<String, String> entry : rawMap.entrySet()) {
            try {
                String productName = entry.getKey();
                ProductCategory productCategory = ProductCategory.valueOf(entry.getValue());
                categoryMap.put(productName, productCategory);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("讀取YAML檔錯誤: " + YAML_PATH, e);
            }
        }
    }

    public static ProductCategory getCategory(String productName) {
        ProductCategory productCategory = categoryMap.get(productName);
        if (productCategory == null) {
            throw new RuntimeException("讀取YAML檔錯誤: " + YAML_PATH + " , 不存在productName值:" + productName);
        }
        return productCategory;
    }
}
