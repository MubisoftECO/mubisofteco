package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.repo.RecipeRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceFacade implements RecipeService{

    private static final double ELEMENT_NUM = 20;
    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> getAllRecipes(int pageNum){
        log.info("Fetching all the recipes from the database");
        Pageable pageable = PageRequest.of(pageNum, (int) ELEMENT_NUM);
        return recipeRepository.findAll(pageable).toList();
    }

    @Override
    public List<Recipe> getAllRecipesWithTitleContaining(int pageNum, String keyword) {
        log.info("Fetching all recipes by keyword");
        Pageable pageable = PageRequest.of(pageNum, (int) ELEMENT_NUM);
        return recipeRepository.findDistinctByTitleContaining(keyword, pageable).toList();
    }

    @Override
    public List<Recipe> getAllRecipesByFlags(int pageNum, List<Flag> flags) {
        log.info("Getting recipes by flags");
        Pageable pageable = PageRequest.of(pageNum, (int) ELEMENT_NUM);
        return recipeRepository.findAllByRecipeFlagsIn(flags, pageable).toList();
    }

    @Override
    public List<Recipe> getAllRecipesByFlagsWithTitleContaining(int pageNum, List<Flag> flags, String keyword) {
        log.info("Getting recipes by flags and keyword");
        Pageable pageable = PageRequest.of(pageNum, (int) ELEMENT_NUM);
        return recipeRepository.findDistinctByRecipeFlagsInAndTitleContaining(flags, keyword, pageable).toList();
    }

    @Override
    public List<Recipe> getRecipesByAuthor(AppUser author) {
        return recipeRepository.findRecipesByAuthor(author);
    }

    @Override
    public void saveRecipe(Recipe recipe){
        log.info("Saving recipe {} on the database.", recipe.getTitle());
        recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipe(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public Recipe editRecipe(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public double countPages() {
        return Math.ceil(recipeRepository.count() / ELEMENT_NUM);
    }

    @Override
    public double countPages(List<Flag> flags) {
        return Math.ceil((recipeRepository.countAllByRecipeFlagsIn(flags)) / ELEMENT_NUM);
    }

    @Override
    public double countPages(List<Flag> flags, String keyword) {
        return Math.ceil(
                recipeRepository.countDistinctByRecipeFlagsInAndTitleContaining(flags, keyword) / ELEMENT_NUM
        );
    }

    @Override
    public double countPages(String keyword) {
        return Math.ceil(recipeRepository.countDistinctByTitleContaining(keyword) / ELEMENT_NUM);
    }

    @Override
    @Transactional
    public boolean removeRecipe(Long id){
        if (id != null) {
            //borrar primero los steps

            //borrar la receta
            recipeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
