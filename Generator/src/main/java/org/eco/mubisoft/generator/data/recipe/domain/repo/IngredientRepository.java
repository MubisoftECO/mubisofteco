package org.eco.mubisoft.generator.data.recipe.domain.repo;

import org.eco.mubisoft.generator.data.recipe.domain.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
