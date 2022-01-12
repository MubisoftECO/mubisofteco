package org.eco.mubisoft.good_and_cheap.recipe.domain.repo;

import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RecipeRepository extends JpaRepository <Recipe, Long> {

    List<Recipe> findRecipesByAuthor(AppUser author);
    Page<Recipe> findAll(Pageable pageable);

}
