package org.eco.mubisoft.good_and_cheap.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name_es;
    private String name_en;
    private String name_eu;
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

}
