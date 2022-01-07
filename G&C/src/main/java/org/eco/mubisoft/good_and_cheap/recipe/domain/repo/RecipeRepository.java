package org.eco.mubisoft.good_and_cheap.recipe.domain.repo;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository <Recipe, Long> {
    List<Recipe> findRecipesByAuthor(AppUser athor);
}
