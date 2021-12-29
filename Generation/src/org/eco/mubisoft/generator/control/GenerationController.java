package org.eco.mubisoft.generator.control;

import org.eco.mubisoft.data.product.dao.ProductFacade;

import java.util.ArrayList;
import java.util.List;

public class GenerationController {

    private final ProductFacade productFacade = new ProductFacade();

    public void generate(int quantity) {
        Log.info("Starting product generation [" + quantity + "]");
        productFacade.generateProducts(quantity);
        Log.info("Products generated.");
    }

    public void generateBasic() {
        // Clear all
        productFacade.deleteAll();

        // Generate product families
        productFacade.generateProductFamilies();
        Log.info("Product families generated.");

        // Generate product types
        productFacade.generateProductTypes();
        Log.info("Product types generated.");

        // Generate flags

        // Generate roles

        // Generate Users

    }

}
