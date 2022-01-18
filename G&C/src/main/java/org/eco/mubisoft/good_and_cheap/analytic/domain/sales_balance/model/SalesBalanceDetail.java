package org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesBalanceDetail {
    private String productName;
    private Double percentage;
    private String reason;

    public SalesBalanceDetail(String productName,String reason, Double percentage) {
        this.productName = productName;
        this.percentage = percentage;
        this.reason = reason;
    }
}
