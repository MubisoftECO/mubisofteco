package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import java.util.List;

public interface RecipeService {
        List<Recipe> getAllRecipes(int pageNum);
        double countPages();
        void saveRecipe(Recipe recipe);
        Recipe getRecipe (Long id);
        void addRecipe(Recipe recipe);
        List<Recipe> getRecipesByAuthor(AppUser author);
        boolean removeRecipe(Long id);
}
