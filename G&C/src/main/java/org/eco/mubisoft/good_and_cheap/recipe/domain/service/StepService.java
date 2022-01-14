package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Step;

import java.util.List;

public interface StepService {
    List<Step> getStepsByRecipe(Recipe recipe);

    void deleteRecipeSteps(Recipe recipe);
}
