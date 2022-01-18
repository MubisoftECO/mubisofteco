package org.eco.mubisoft.good_and_cheap.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class IngredientId implements Serializable {

    @Column(name = "recipe_id")
    private Long recipe;

    @Column(name = "product_type_id")
    private Long productType;

    @Override
    public boolean equals(Object object) {
        IngredientId id = (IngredientId) object;
        return (Objects.equals(id.getProductType(), this.getProductType())) && (Objects.equals(id.getRecipe(), this.getRecipe()));
    }

    @Override
    public int hashCode() {
        return this.recipe.hashCode() + this.productType.hashCode();
    }
}
