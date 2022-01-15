package org.eco.mubisoft.data.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.eco.mubisoft.data.user.model.AppUser;

import java.util.Date;

@Getter
@AllArgsConstructor
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

    public Long getProductType() {
        return productType.getId();
    }

    public String getRemoveReason() {
        return removeReason.toString();
    }

    public Long getVendor() {
        return vendor.getId();
    }
}
