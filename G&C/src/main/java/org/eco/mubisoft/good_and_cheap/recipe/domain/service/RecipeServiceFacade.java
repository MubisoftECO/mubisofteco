package org.eco.mubisoft.good_and_cheap.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.repo.RecipeRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceFacade implements RecipeService{
    private final RecipeRepository recipeRepository;

    @Override
    public List<Recipe> getAllRecipes(){
        log.info("Fetching all the recipes from the database");
        //Aqui marca un code smell. Segun sonar, la funcion findAll no devuelve null. Pero según internet, si.
        //Asi que compruebo si es null para devolver una lista vacia en vez de un null. Para que luego el
        //programa no se queje.
        List<Recipe> recipeList = recipeRepository.findAll();
        if (recipeList == null){
            recipeList = new ArrayList<>();
        }
        return recipeList;
    }

    @Override
    public Recipe saveRecipe(Recipe recipe){
        log.info("Saving recipe {} on the database.", recipe.getTitle());
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipe(Long id){return recipeRepository.findById(id).orElse(null);}

    @Override
    public void addRecipe(Recipe recipe) { recipeRepository.save(recipe);}

    @Override
    public boolean removeRecipe(Long id){
        if(id == null) return false;
        recipeRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Recipe> getRecipesByAuthor(AppUser author) {
        return recipeRepository.findRecipesByAuthor(author);
    }
}
