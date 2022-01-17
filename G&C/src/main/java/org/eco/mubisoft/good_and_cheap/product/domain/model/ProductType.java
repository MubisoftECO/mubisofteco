package org.eco.mubisoft.good_and_cheap.product.domain.model;

import lombok.*;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Ingredient;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameEs;
    private String nameEn;
    private String nameEu;
    private String imgSrc = null;
    private String measurementUnit;
    @ManyToOne(fetch = FetchType.EAGER)
    private ProductFamily productFamily;
    @OneToMany(mappedBy = "productType")
    private Collection<Ingredient> ingredient;

}
