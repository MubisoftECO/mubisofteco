package org.eco.mubisoft.generator.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.generator.data.product.domain.service.ProductService;
import org.eco.mubisoft.generator.data.recipe.domain.service.RecipeService;
import org.eco.mubisoft.generator.data.user.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GenerationController {

    private static final int GENERATION_QUANTITY = 100;

    private final ProductService productService;
    private final RecipeService recipeService;
    private final UserService userService;

    @GetMapping("/generate{basic}{quantity}")
    public String startGeneration(
            @PathVariable(name = "basic", required = false) Boolean generateBasicValues,
            @PathVariable(name = "quantity", required = false) Integer generationQuantity
    ) {
        if (generateBasicValues == null || generateBasicValues) {
            log.info("Generating Basic Elements");
            generateBasic();
        }
        log.info("Generating Random Elements");
        generate((generationQuantity != null) ? generationQuantity : GENERATION_QUANTITY);
        log.info("Generation process finished.");

        return "index";
    }

    public void generate(int quantity) {
        log.info("Starting product generation [" + quantity + "].");
        productService.generateProducts(quantity);
        log.info("Products generated.");

        log.info("Starting recipe generation [" + quantity + "].");
        recipeService.generateRecipes(quantity);
        log.info("Recipes generated.");
    }

    public void generateBasic() {
        // Clear all
        recipeService.deleteAll();
        productService.deleteAll();
        userService.deleteAll();

        // Generate product families
        productService.generateProductFamilies();
        log.info("Product families generated.");

        // Generate product types
        productService.generateProductTypes();
        log.info("Product types generated.");

        // Generate flags
        recipeService.generateRecipeFlags();
        log.info("Flags generated.");

        // Generate roles
        userService.generateRoles();
        log.info("User roles generated.");

        // Generate Locations
        userService.generateLocations();
        log.info("Locations generated.");

        // Generate Users
        userService.generateAppUsers();
        log.info("App users generated.");
    }

}
