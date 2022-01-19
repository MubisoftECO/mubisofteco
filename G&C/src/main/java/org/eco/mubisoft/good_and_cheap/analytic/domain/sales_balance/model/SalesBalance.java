package org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
public class SalesBalance {
    private String productName;
    private Map<String, Double> percentage;

    public SalesBalance(String productName) {
        this.productName = productName;
        this.percentage = new HashMap<>();
    }

    public void save(String key, Double percentage) {
        this.percentage.put(key,percentage);
    }
}
