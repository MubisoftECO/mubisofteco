package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Ingredient;

public interface IngredientFacade {

    Ingredient saveIngredient(Ingredient ingredient);

}
