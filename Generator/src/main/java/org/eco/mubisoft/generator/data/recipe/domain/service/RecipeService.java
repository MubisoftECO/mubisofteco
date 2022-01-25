package org.eco.mubisoft.generator.data.recipe.domain.service;

public interface RecipeService {

    void generateRecipeFlags();
    void generateRecipes(int quantity);
    void generateIngredients();
    void deleteAll();

}
