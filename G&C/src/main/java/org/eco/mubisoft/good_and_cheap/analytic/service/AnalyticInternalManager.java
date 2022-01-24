package org.eco.mubisoft.good_and_cheap.analytic.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class AnalyticInternalManager {
    private int countSales;
    private int countBusiness;
    private List<Double> expired;
    private List<Double> sold;
    private List<Double> other;
    private List<String> productName;
    private List<String> reason;
    private List<Double> total;

    public AnalyticInternalManager() {
        expired = new ArrayList<>();
        sold = new ArrayList<>();
        other  = new ArrayList<>();
        productName  = new ArrayList<>();
        reason = new ArrayList<>();
        total = new ArrayList<>();
        countSales = 0;
        countBusiness = 0;
    }


    public void addProduct(String p) {productName.add(p);}
    public void addReason(String s) {reason.add(s);}
    public void addTotal(Double t) {total.add(t);}

    public void addPriceByReason(Double value, String reason) {
        switch (reason) {
            case "SOLD":
                sold.add(value);
                break;
            case "EXPIRED":
                expired.add(value);
                break;
            default:
                other.add(value);
        }
    }

}
