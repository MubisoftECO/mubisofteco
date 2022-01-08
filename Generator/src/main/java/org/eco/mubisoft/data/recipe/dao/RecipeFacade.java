package org.eco.mubisoft.data.recipe.dao;

import org.eco.mubisoft.data.product.dao.ProductMysqlDAO;
import org.eco.mubisoft.data.product.model.ProductType;
import org.eco.mubisoft.data.recipe.model.Flag;
import org.eco.mubisoft.data.recipe.model.Recipe;
import org.eco.mubisoft.data.recipe.model.RecipeNames;
import org.eco.mubisoft.data.recipe.model.Step;
import org.eco.mubisoft.data.user.dao.UserDAO;
import org.eco.mubisoft.data.user.dao.UserMysqlDAO;
import org.eco.mubisoft.data.user.model.AppUser;
import org.eco.mubisoft.generator.connection.FileReader;
import org.eco.mubisoft.generator.control.Log;

import java.util.*;

public class RecipeFacade {

    private final RecipeDAO recipeDAO = new RecipeMysqlDAO();
    private final FileReader fileReader = new FileReader();
    private final UserDAO userDAO = new UserMysqlDAO();

    private final List<String> languageList = Arrays.asList(
            "en", "es", "eu"
    );

    private final List<Flag> flagList = Arrays.asList(
            new Flag("Lactose", "Lactosa", "Laktosa"),
            new Flag("Egg", "Huevo", "Arrautza"),
            new Flag("Fish", "Pescado", "Arraina"),
            new Flag("Soy", "Soja", "Soja"),
            new Flag("Celery", "Apio", "Apioa"),
            new Flag("Mustard", "Mostaza", "Mostaza"),
            new Flag("Sulfur Dioxide", "Dioxido de Azufre", "Sufre Dioxidoa"),
            new Flag("Lupins", "Altramuces", "Lupinak"),
            new Flag("Seafood", "Crustaceo", "Krustazeoa"),
            new Flag("Gluten", "Gluten", "Glutena"),
            new Flag("Nut", "Frutos Secos", "Fruitu Lehorrak"),
            new Flag("Vegetarian", "Vegetariano", "Begetarianoa"),
            new Flag("Vegan", "Vegano", "Beganoa")
    );

    public void generateRecipeFlags() {
        recipeDAO.insertRecipeFlags(flagList);
    }

    public void generateRecipes(int quantity) {
        List<ProductType> productTypes = new ProductMysqlDAO().getProductTypes();
        List<RecipeNames> recipeNames = fileReader.getRecipeNames();
        List<Flag> flagList = recipeDAO.getRecipeFlags();
        List<Long> appUsers = userDAO.getAppUserIds();
        long currentMaxID = recipeDAO.getRecipeID();
        Random random = new Random();
        long totalSteps = 1L;

        for (long id = currentMaxID; id < currentMaxID + quantity; id++) {
            String language = languageList.get(random.nextInt(languageList.size()));
            String title = recipeNames.get(random.nextInt(recipeNames.size())).getNameForLanguage(
                    language, productTypes.get(random.nextInt(productTypes.size())).getNameForLanguage(language)
            );
            String description = this.getDescriptionForLanguage(language, title);
            Collection<ProductType> recipeIngredients = new ArrayList<>();
            Collection<Flag> recipeFlags = new ArrayList<>();

            int flagCount = random.nextInt(5);
            int ingredientCount = random.nextInt(10) + 3;

            for (int i = 0; i < flagCount; i++) {
                Flag flag = flagList.get(random.nextInt(flagList.size()));
                if (!recipeFlags.contains(flag)) {
                    recipeFlags.add(flag);
                } else {
                    i--;
                }
            }
            for (int i = 0; i < ingredientCount; i++) {
                ProductType ingredient = productTypes.get(random.nextInt(productTypes.size()));
                if (!recipeIngredients.contains(ingredient)) {
                    recipeIngredients.add(ingredient);
                } else {
                    i--;
                }
            }
            Recipe recipe = new Recipe(
                    id, title, description, language, random.nextInt(240) + 10,
                    new AppUser(appUsers.get(random.nextInt(appUsers.size()))), recipeIngredients, recipeFlags
            );
            recipeDAO.insertRecipe(recipe);
            recipeDAO.setRecipeFlag(id, recipe.getRecipeFlags());
            recipeDAO.setRecipeIngredients(id, recipe.getIngredients());

            int stepCount = random.nextInt(8) + 2;
            for (int i = 0; i < stepCount; i++) {
                recipeDAO.insertRecipeStep(new Step(
                        totalSteps++, i + 1,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
                                "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                        recipe
                ));
            }

            // display generation percentage
            if ((id - currentMaxID) / (quantity / 10) > 0 && (id - currentMaxID) % (quantity / 10) == 0) {
                Log.info("\t- Generation at " + ((id - currentMaxID) / (quantity / 10) * 10) + "%.");
            }
        }
    }

    public void deleteAll() {
        recipeDAO.deleteRecipeFlags();
        recipeDAO.deleteRecipeSteps();
        recipeDAO.deleteRecipeIngredients();
        recipeDAO.deleteRecipes();
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
