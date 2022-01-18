package org.eco.mubisoft.generator.data.recipe.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.generator.data.product.domain.model.ProductType;
import org.eco.mubisoft.generator.data.product.domain.repo.ProductTypeRepository;
import org.eco.mubisoft.generator.data.recipe.domain.model.*;
import org.eco.mubisoft.generator.data.recipe.domain.repo.FlagRepository;
import org.eco.mubisoft.generator.data.recipe.domain.repo.IngredientRepository;
import org.eco.mubisoft.generator.data.recipe.domain.repo.RecipeRepository;
import org.eco.mubisoft.generator.data.recipe.domain.repo.StepRepository;
import org.eco.mubisoft.generator.data.user.domain.model.AppUser;
import org.eco.mubisoft.generator.data.user.domain.repo.UserRepository;
import org.eco.mubisoft.generator.connection.FileReader;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceFacade implements RecipeService{

    private final List<String> languageList = Arrays.asList(
            "en", "es", "eu"
    );

    private final List<Flag> flagList = Arrays.asList(
            new Flag(1L, "Lactose Free", "Sin Lactosa", "Laktosarik Gabe"),
            new Flag(2L, "Egg Free", "Sin Huevo", "Arrautzik Gabe"),
            new Flag(3L, "Fish Free", "Sin Pescado", "Arrainik Gabe"),
            new Flag(4L, "Soy Free", "Sin Soja", "Sojarik Gabe"),
            new Flag(5L, "Celery Free", "Sin Apio", "Apiorik Gabe"),
            new Flag(6L, "Mustard Free", "Sin Mostaza", "Mostazarik Gabe"),
            new Flag(7L, "Sulfur Dioxide Free", "Sin Dioxido de Azufre", "Sufre Dioxidorik Gabe"),
            new Flag(8L, "Lupins Free", "Sin Altramuces", "Lupinarik Gabe"),
            new Flag(9L, "Seafood Free", "Sin Crustaceo", "Krustazerik Gabe"),
            new Flag(10L, "Gluten Free", "Sin Gluten", "Glutenik Gabe"),
            new Flag(11L, "Nut Free", "Sin Frutos Secos", "Fruitu Lehorrik Gabe"),
            new Flag(12L, "Vegetarian", "Vegetariano", "Begetarianoa"),
            new Flag(13L, "Vegan", "Vegano", "Beganoa")
    );

    private final FileReader fileReader = new FileReader();
    private final StepRepository stepRepository;
    private final FlagRepository flagRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final ProductTypeRepository productTypeRepository;

    @Override
    public void generateRecipeFlags() {
        flagRepository.saveAll(flagList);
    }

    @Override
    public void generateRecipes(int quantity) {
        List<ProductType> productTypes = productTypeRepository.findAll();
        List<RecipeNames> recipeNames = fileReader.getRecipeNames();
        List<AppUser> appUsers = userRepository.findAll();
        List<Flag> flagList = flagRepository.findAll();
        Random random = new Random();

        for (long i = 0; i < quantity; i++) {
            RecipeNames recipeName = recipeNames.get(random.nextInt(recipeNames.size()));
            Collection<Ingredient> recipeIngredients = new ArrayList<>();
            Collection<Flag> recipeFlags = new ArrayList<>();
            Recipe recipe = new Recipe();

            recipe.setLanguage(languageList.get(random.nextInt(languageList.size())));
            recipe.setTitle(recipeName.getNameForLanguage(
                    recipe.getLanguage(),
                    productTypes.get(random.nextInt(productTypes.size())).getNameForLanguage(recipe.getLanguage())
            ));
            recipe.setDescription(this.getDescriptionForLanguage(recipe.getLanguage(), recipe.getTitle()));
            recipe.setTimeInMinutes(random.nextInt(240) + 10);
            recipe.setAuthor(appUsers.get(random.nextInt(appUsers.size())));

            int flagCount = random.nextInt(5);
            int ingredientCount = random.nextInt(10) + 3;

            for (int j = 0; j < flagCount; j++) {
                Flag flag = flagList.get(random.nextInt(flagList.size()));
                if (!recipeFlags.contains(flag)) {
                    recipeFlags.add(flag);
                } else {
                    j--;
                }
            }
            recipe.setRecipeFlags(recipeFlags);

            for (int j = 0; j < ingredientCount; j++) {
                Ingredient ingredient = new Ingredient(
                        recipe,
                        productTypes.get(random.nextInt(productTypes.size())),
                        random.nextInt(20) + 1
                );
                if (!recipeIngredients.contains(ingredient)) {
                    recipeIngredients.add(ingredient);
                } else {
                    j--;
                }
            }
            recipe.setIngredients(recipeIngredients);

            // Save recipe
            recipeRepository.save(recipe);

            // Add steps
            int stepCount = random.nextInt(8) + 2;
            for (int j = 0; j < stepCount; j++) {
                stepRepository.save(new Step(
                        j + 1,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                        recipe
                ));
            }

            // display generation percentage
            if (i / (quantity / 10) > 0 && i % (quantity / 10) == 0) {
                log.info("Generation at " + (i / (quantity / 10) * 10) + "%.");
            }
        }
    }

    @Override
    public void generateIngredients() {
        List<Recipe> recipeList = recipeRepository.findAll();
        List<ProductType> productTypeList = productTypeRepository.findAll();
        Random random = new Random();

        recipeList.forEach(recipe -> {
            for (int i = 0; i < random.nextInt(20) + 2; i++) {
                ingredientRepository.save(new Ingredient(
                        recipe,
                        productTypeList.get(random.nextInt(productTypeList.size())),
                        random.nextInt(100) + 1
                ));
            }
        });
    }

    @Override
    public void deleteAll() {
        flagRepository.deleteAll();
        stepRepository.deleteAll();
        ingredientRepository.deleteAll();
        recipeRepository.deleteAll();
    }

    private String getDescriptionForLanguage(String language, String title) {
        switch (language) {
            case "en":
                return "Following this recipe you will be able to easily make an incredible " + title + " for you " +
                        "and your family. I hope you enjoy!";
            case "es":
                return "Siguiendo esta receta seras capaz de realizar un increible " + title + " para ti y para tu " +
                        "familia. ¡Espero que lo disfrutes!";
            case "eu":
                return "Errezeta hau jarraituz, " + title + " ikaragarri bat lortuko duzu, bai zuretzat bai zure " +
                        "familiarentzat. Espero dut zure gustokoa izatea!";
            default: return null;
        }
    }

}
