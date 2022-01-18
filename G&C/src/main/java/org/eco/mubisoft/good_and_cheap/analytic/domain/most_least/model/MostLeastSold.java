package org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model;

import lombok.Data;

@Data
public class MostLeastSold {
    private int order;
    private String name_en;
    private String name_es;
    private String name_eu;
    private Double total;
    private MostLeastSoldType type;

    public MostLeastSold(int order, String name_en, String name_eu, String name_es, Double total, MostLeastSoldType type) {
        this.order = order;
        this.name_en = name_en;
        this.name_eu = name_eu;
        this.name_es = name_es;
        this.total = total;
        this.type = type;
    }
}
