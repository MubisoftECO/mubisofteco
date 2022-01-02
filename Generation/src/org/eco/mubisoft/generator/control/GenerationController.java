package org.eco.mubisoft.generator.control;

import org.eco.mubisoft.data.product.dao.ProductFacade;
import org.eco.mubisoft.data.recipe.dao.RecipeFacade;

import java.util.ArrayList;
import java.util.List;

public class GenerationController {

    private final ProductFacade productFacade = new ProductFacade();
    private final RecipeFacade recipeFacade = new RecipeFacade();

    public void generate(int quantity) {
        Log.info("Starting product generation [" + quantity + "].");
        // productFacade.generateProducts(quantity);
        Log.info("Products generated.");

        Log.info("Starting recipe generation [" + quantity + "].");
        recipeFacade.generateRecipes();
        System.out.println("Las recetas no se generan, pero weno.");
        Log.info("Recipes generated.");
    }

    public void generateBasic() {
        // Clear all
        recipeFacade.deleteAll();
        productFacade.deleteAll();

        // Generate product families
        productFacade.generateProductFamilies();
        Log.info("Product families generated.");

        // Generate product types
        productFacade.generateProductTypes();
        Log.info("Product types generated.");

        // Generate flags
        recipeFacade.generateRecipeFlags();
        Log.info("Flags generated.");

        // Generate roles

        // Generate Locations

        // Generate Users

    }

}
