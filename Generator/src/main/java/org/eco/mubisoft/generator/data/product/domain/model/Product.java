package org.eco.mubisoft.generator.data.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eco.mubisoft.generator.data.user.domain.model.AppUser;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;
    private String nameEs;
    private String nameEn;
    private String nameEu;
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductType productType;
    private Double price;
    private Double quantity;
    private Date publishDate;
    private Date expirationDate;
    private Date removedDate;
    private String removeReason;
    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser vendor;

    public Product(String nameEs, String nameEn, String nameEu, ProductType productType, Double price, Double quantity,
                   Date publishDate, Date expirationDate, Date removedDate, String removeReason, AppUser vendor) {
        this.nameEs = nameEs;
        this.nameEn = nameEn;
        this.nameEu = nameEu;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
        this.publishDate = publishDate;
        this.expirationDate = expirationDate;
        this.removedDate = removedDate;
        this.removeReason = removeReason;
        this.vendor = vendor;
    }
}
