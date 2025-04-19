package com.unistar.shop.enums;

import com.unistar.shop.config.LocationTaxRatesConfig;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Location {

    CA {
        @Override
        public BigDecimal getSalesTaxRate(ProductCategory category) {
            if (category == ProductCategory.FOOD) {
                return BigDecimal.ZERO;
            }
            return LocationTaxRatesConfig.getTaxRates(CA);
        }
    },
    NY {
        @Override
        public BigDecimal getSalesTaxRate(ProductCategory category) {
            if (category == ProductCategory.FOOD || category == ProductCategory.CLOTHING) {
                return BigDecimal.ZERO;
            }
            return LocationTaxRatesConfig.getTaxRates(NY);
        }
    },
    ;

    // 獲取商品稅率
    public abstract BigDecimal getSalesTaxRate(ProductCategory category);
}