package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.repo.RecipeRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceFacade implements RecipeService{

    private static final int ELEMENT_NUM = 20;
    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> getAllRecipes(int pageNum){
        log.info("Fetching all the recipes from the database");
        Pageable pageable = PageRequest.of(pageNum, ELEMENT_NUM);
        return recipeRepository.findAll(pageable).toList();
    }

    @Override
    public double countPages() {
        return Math.ceil(recipeRepository.count() / ELEMENT_NUM);
    }

    @Override
    public void saveRecipe(Recipe recipe){
        log.info("Saving recipe {} on the database.", recipe.getTitle());
        recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipe(Long id){ return recipeRepository.findById(id).orElse(null); }

    @Override
    public void addRecipe(Recipe recipe) { recipeRepository.save(recipe);}

    @Override
    public boolean removeRecipe(Long id){
        if (id != null) {
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Recipe> getRecipesByAuthor(AppUser author) {
        return recipeRepository.findRecipesByAuthor(author);
    }
}
