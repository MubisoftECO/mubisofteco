package org.eco.mubisoft.generator.data.recipe.domain.repo;

import org.eco.mubisoft.generator.data.recipe.domain.model.Flag;
import org.eco.mubisoft.generator.data.recipe.domain.model.Recipe;
import org.eco.mubisoft.generator.data.user.domain.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository <Recipe, Long> {

    List<Recipe> findRecipesByAuthor(AppUser author);
    Page<Recipe> findAll (Pageable pageable);
    Page<Recipe> findDistinctByTitleContaining(String keyword, Pageable pageable);
    Double countDistinctByTitleContaining(String keyword);
    boolean deleteRecipeById(Long id);
    Double countAllByRecipeFlagsIn(List<Flag> flags);
    Page<Recipe> findAllByRecipeFlagsIn(List<Flag> flags, Pageable pageable);
    Double countDistinctByRecipeFlagsInAndTitleContaining(List<Flag> flags, String title);
    Page<Recipe> findDistinctByRecipeFlagsInAndTitleContaining(List<Flag> flags, String title, Pageable pageable);
}
