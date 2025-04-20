package com.unistar.shop.config;

import com.unistar.shop.enums.Location;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class LocationTaxRatesConfig {

    private static final String YAML_PATH = "/location-tax-rates.yml";
    private static final Map<Location, BigDecimal> LOCATION_TAX_RATES_MAP = new HashMap<>();

    private LocationTaxRatesConfig() {
    }

    // 啟動時讀取
    static {
        Yaml yaml = new Yaml();
        InputStream inputStream = LocationTaxRatesConfig.class.getResourceAsStream(YAML_PATH);
        if (inputStream == null) {
            throw new RuntimeException("讀取YAML檔錯誤: " + YAML_PATH + " 不存在");
        }

        Map<String, BigDecimal> rawMap = yaml.load(inputStream);
        for (Map.Entry<String, BigDecimal> entry : rawMap.entrySet()) {
            try {
                Location location = Location.valueOf(entry.getKey());
                BigDecimal taxRates = BigDecimal.valueOf(((Number) entry.getValue()).doubleValue());
                LOCATION_TAX_RATES_MAP.put(location, taxRates);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("讀取YAML檔錯誤: " + YAML_PATH, e);
            }
        }
    }

    public static BigDecimal getTaxRates(Location location) {
        BigDecimal taxRates = LOCATION_TAX_RATES_MAP.get(location);
        if (taxRates == null) {
            throw new RuntimeException("讀取YAML檔錯誤: " + YAML_PATH + " , 不存在location值:" + location.name());
        }
        return taxRates;
    }
}
