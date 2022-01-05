package org.eco.mubisoft.data.recipe.dao;

import org.eco.mubisoft.data.product.model.ProductType;
import org.eco.mubisoft.data.recipe.model.Flag;
import org.eco.mubisoft.data.recipe.model.Recipe;
import org.eco.mubisoft.data.recipe.model.Step;

import java.util.Collection;
import java.util.List;

public interface RecipeDAO {

    void insertRecipeFlags(List<Flag> flagList);
    List<Flag> getRecipeFlags();
    void setRecipeFlag(Long recipeID, Collection<Flag> flagList);
    void deleteRecipeFlags();
    void setRecipeIngredients(Long recipeID, Collection<ProductType> ingredients);
    void deleteRecipeIngredients();
    void insertRecipe(Recipe recipe);
    void deleteRecipes();
    void insertRecipeStep(Step step);
    void deleteRecipeSteps();
    long getRecipeID();
}
