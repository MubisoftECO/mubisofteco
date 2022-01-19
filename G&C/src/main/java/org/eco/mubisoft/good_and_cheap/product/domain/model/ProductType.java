package org.eco.mubisoft.good_and_cheap.product.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Ingredient;

import javax.persistence.*;
import java.util.Collection;

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

    @OneToMany(mappedBy = "productType", fetch = FetchType.EAGER)
    @JsonBackReference
    private Collection<Ingredient> ingredient;

    public ProductType(Long id, String nameEs, String nameEn, String nameEu, String measurementUnit,
                       ProductFamily productFamily) {
        this.id = id;
        this.nameEs = nameEs;
        this.nameEn = nameEn;
        this.nameEu = nameEu;
        this.measurementUnit = measurementUnit;
        this.productFamily = productFamily;
    }
}
