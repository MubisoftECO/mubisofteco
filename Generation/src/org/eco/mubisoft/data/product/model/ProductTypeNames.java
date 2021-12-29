package org.eco.mubisoft.data.product.model;

public class ProductTypeNames {

    public Long id;
    public String en;
    public String es;
    public String eu;

    public ProductTypeNames(String es, String eu, String en, Long id) {
        this.id = id;
        this.en = en;
        this.es = es;
        this.eu = eu;
    }

}