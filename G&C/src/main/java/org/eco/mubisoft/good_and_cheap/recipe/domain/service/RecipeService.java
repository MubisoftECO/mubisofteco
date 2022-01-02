package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;

import java.util.List;

public interface RecipeService {
        List<Recipe> getAllRecipes();
        Recipe saveRecipe(Recipe recipe);
        Recipe getRecipe (Long id);
        void addRecipe(Recipe recipe);

        boolean removeRecipe(Long id);
}
