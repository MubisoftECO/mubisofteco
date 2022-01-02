package org.eco.mubisoft.data.recipe.dao;

import org.eco.mubisoft.data.product.dao.ProductDAO;
import org.eco.mubisoft.data.product.dao.ProductMysqlDAO;
import org.eco.mubisoft.data.product.model.ProductType;
import org.eco.mubisoft.data.recipe.model.Flag;
import org.eco.mubisoft.data.recipe.model.RecipeNames;
import org.eco.mubisoft.generator.connection.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RecipeFacade {

    private final RecipeDAO recipeDAO = new RecipeMysqlDAO();
    private final FileReader fileReader = new FileReader();

    private final List<Flag> flagList = Arrays.asList(
            new Flag("Lactose", "Lactosa", "Laktosa"),
            new Flag("Egg", "Huevo", "Arrautza"),
            new Flag("Fish", "Pescado", "Arraina"),
            new Flag("Soy", "Soja", "Soja"),
            new Flag("Celery", "Apio", "Apioa"),
            new Flag("Mustard", "Mostaza", "Mostaza"),
            new Flag("Sulfur Dioxide", "Dioxido de Azufre", "Sufre Dioxidoa"),
            new Flag("Lupins", "Altamuces", "Lupinak"),
            new Flag("Seafood", "Crustaceo", "Krustazeoa"),
            new Flag("Gluten", "Gluten", "Glutena"),
            new Flag("Nut", "Frutos Secos", "Fruitu Lehorrak"),
            new Flag("Vegetarian", "Vegetariano", "Begetarianoa"),
            new Flag("Vegan", "Vegano", "Beganoa")
    );

    public void generateRecipeFlags() {
        recipeDAO.insertRecipeFlags(flagList);
    }

    public void generateRecipes() {
        List<RecipeNames> recipeNames = fileReader.getRecipeNames();
        List<ProductType> productTypes = new ProductMysqlDAO().getProductTypes();

        recipeNames.forEach(name -> {
            ProductType product = productTypes.get(new Random().nextInt(productTypes.size()));
            name.setName_en(product.getName_en());
            name.setName_es(product.getName_es());
            name.setName_eu(product.getName_eu());
        });

    }

    public void deleteAll() {
        recipeDAO.deleteRecipeFlags();
        recipeDAO.deleteRecipeSteps();
        recipeDAO.deleteRecipes();
    }

}
