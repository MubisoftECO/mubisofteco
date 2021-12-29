package org.eco.mubisoft.data.product.model;

import org.eco.mubisoft.data.user.model.AppUser;

import java.util.Date;

public class Product {

    private Long id;
    private String name_en;
    private String name_es;
    private String name_eu;
    private ProductType productType;
    private Double price;
    private Double quantity;
    private Date publishDate;
    private Date expirationDate;
    private Date removedDate;
    private RemoveReason removeReason;
    private AppUser vendor;

    public Product(Long id, String name_en, String name_es, String name_eu, ProductType productType, Double price,
                   Double quantity, Date publishDate, Date expirationDate, Date removedDate, RemoveReason removeReason,
                   AppUser vendor) {
        this.id = id;
        this.name_en = name_en;
        this.name_es = name_es;
        this.name_eu = name_eu;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
        this.publishDate = publishDate;
        this.expirationDate = expirationDate;
        this.removedDate = removedDate;
        this.removeReason = removeReason;
        this.vendor = vendor;
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

    public Long getProductType() {
        return productType.getId();
    }

    public Double getPrice() {
        return price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Date getRemovedDate() {
        return removedDate;
    }

    public String getRemoveReason() {
        return removeReason.toString();
    }

    public Long getVendor() {
        try {
            return vendor.getId();
        } catch (NullPointerException e) {
            return 1L;
        }
    }
}
