package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import javax.xml.stream.FactoryConfigurationError;
import java.util.Collection;
import java.util.List;

public interface RecipeService {

        List<Recipe> getAllRecipes(int pageNum);
        List<Recipe> getAllRecipesWithTitleContaining(int pageNum, String keyword);
        List<Recipe> getAllRecipesByFlags(int pageNum, List<Flag> flags);
        List<Recipe> getAllRecipesByFlagsWithTitleContaining (int pageNum, List<Flag> flags, String keyword);
        List<Recipe> getRecipesByAuthor(AppUser author);
        void saveRecipe(Recipe recipe);
        Recipe getRecipe (Long id);
        Recipe editRecipe (Long id);
        double countPages();
        double countPages(String keyword);
        double countPages(List<Flag> flags);
        double countPages(List<Flag> flags, String keyword);
        boolean removeRecipe(Long id);

}
