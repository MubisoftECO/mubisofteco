package org.eco.mubisoft.data.product.model;

public class ProductType {

    private Long id;
    private String name_en;
    private String name_es;
    private String name_eu;
    private String imgSrc = null;
    private MeasurementUnit measurementUnit;
    private ProductFamily productFamily;

    public ProductType(Long id, String name_en, String name_es, String name_eu, MeasurementUnit measurementUnit, ProductFamily productFamily) {
        this.id = id;
        this.name_en = name_en;
        this.name_es = name_es;
        this.name_eu = name_eu;
        this.measurementUnit = measurementUnit;
        this.productFamily = productFamily;
    }

    public Long getId() {
        return id;
    }

    public String getName_en() {
        return name_en;
    }

    public String getName_es() {
        return name_es;
    }

    public String getName_eu() {
        return name_eu;
    }

    public String getMeasurementUnit() {
        return measurementUnit.toString();
    }

    public Long getProductFamily() {
        return productFamily.getId();
    }
}
