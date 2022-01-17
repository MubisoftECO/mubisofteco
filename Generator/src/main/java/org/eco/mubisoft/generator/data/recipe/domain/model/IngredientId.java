package org.eco.mubisoft.generator.data.recipe.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class IngredientId implements Serializable {

    private Long recipe;
    private Long productType;

    @Override
    public boolean equals(Object object) {
        if (object instanceof IngredientId) {
            IngredientId otherId = (IngredientId) object;
            return (Objects.equals(otherId.recipe, this.recipe))
                    && (Objects.equals(otherId.productType, this.productType));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (int)(recipe + productType);
    }

}
