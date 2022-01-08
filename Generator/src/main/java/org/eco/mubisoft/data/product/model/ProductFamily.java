package org.eco.mubisoft.data.product.model;

public class ProductFamily {

    private Long id;
    private String name_es;
    private String name_eu;
    private String name_en;

    public ProductFamily(Long id, String name_en, String name_es, String name_eu) {
        this.id = id;
        this.name_es = name_es;
        this.name_eu = name_eu;
        this.name_en = name_en;
    }

    public Long getId() {
        return id;
    }

    public String getName_es() {
        return name_es;
    }

    public String getName_eu() {
        return name_eu;
    }

    public String getName_en() {
        return name_en;
    }
}
