package org.eco.mubisoft.good_and_cheap.product.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name_es;
    private String name_en;
    private String name_eu;
    private String imgSrc = null;
    private String measurementUnit;
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductFamily productFamily;

}
