package org.eco.mubisoft.good_and_cheap.recipe.domain.repo;

import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Ingredient;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.IngredientId;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, IngredientId> {

    Ingredient findByProductType(ProductType productType);
    List<Ingredient> findAllByRecipe(Recipe recipe);

}
