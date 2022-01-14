package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import java.util.List;

public interface RecipeService {

        List<Recipe> getAllRecipes(int pageNum);
        List<Recipe> getRecipesByAuthor(AppUser author);
        void saveRecipe(Recipe recipe);
        Recipe getRecipe (Long id);
        Recipe editRecipe (Long id);
        double countPages();
        boolean removeRecipe(Long id);

}
