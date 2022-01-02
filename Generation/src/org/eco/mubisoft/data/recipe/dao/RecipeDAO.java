package org.eco.mubisoft.data.recipe.dao;

import org.eco.mubisoft.data.recipe.model.Flag;
import org.eco.mubisoft.data.recipe.model.Recipe;
import org.eco.mubisoft.data.recipe.model.Step;

import java.util.List;

public interface RecipeDAO {

    void insertRecipeFlags(List<Flag> flagList);
    List<Flag> getRecipeFlags();
    void deleteRecipeFlags();
    void insertRecipe(Recipe recipe);
    void deleteRecipes();
    void insertRecipeStep(Step step);
    void deleteRecipeSteps();

}
