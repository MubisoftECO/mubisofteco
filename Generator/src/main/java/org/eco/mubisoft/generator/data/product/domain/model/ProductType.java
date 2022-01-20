package org.eco.mubisoft.generator.data.product.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eco.mubisoft.generator.data.recipe.domain.model.Ingredient;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;
    private String nameEs;
    private String nameEn;
    private String nameEu;
    private String imgSrc = null;
    private String measurementUnit;

    @ManyToOne(fetch = FetchType.EAGER)
    private ProductFamily productFamily;

    @OneToMany(mappedBy = "productType")
    @JsonBackReference
    private Collection<Ingredient> ingredient;

    public ProductType(Long id, String nameEs, String nameEn, String nameEu, String imgSrc, String measurementUnit, ProductFamily productFamily) {
        this.id = id;
        this.nameEs = nameEs;
        this.nameEn = nameEn;
        this.nameEu = nameEu;
        this.imgSrc = imgSrc;
        this.measurementUnit = measurementUnit;
        this.productFamily = productFamily;
    }

    public String getNameForLanguage(String language) {
        switch (language) {
            case "en": return this.nameEn;
            case "es": return this.nameEs;
            case "eu": return this.nameEu;
            default: return null;
        }
    }
}
