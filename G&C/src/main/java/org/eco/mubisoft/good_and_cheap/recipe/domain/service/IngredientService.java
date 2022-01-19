package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Ingredient;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;

import java.util.List;

public interface IngredientService {

    Ingredient saveIngredient(Ingredient ingredient);
    List<Ingredient> getRecipeIngredients(Recipe recipe);
    void deleteRecipeIngredients(Recipe recipe);

}
