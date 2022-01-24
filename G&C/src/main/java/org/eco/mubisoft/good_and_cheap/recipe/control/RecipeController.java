package org.eco.mubisoft.good_and_cheap.recipe.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.pages.PageManager;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductTypeService;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.*;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.*;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/recipe")
public class RecipeController {

    private final IngredientService ingredientService;
    private final ProductTypeService productTypeService;
    private final ProductService productService;
    private final RecipeService recipeService;
    private final UserService userService;
    private final FlagService flagService;
    private final StepService stepService;

    @GetMapping("/create")
    public String createRecipe(Model model){
        log.info("Sending user create recipe form");

        model.addAttribute("flagList", flagService.getAllFlags());
        model.addAttribute("ingredientList", productTypeService.getAllProductTypes());
        model.addAttribute("measurementList", productService.getMeasurementUnits());

        return "recipe/recipe_form";
    }

    @PostMapping("/save")
    public String saveRecipe(HttpServletRequest request) {
        Recipe recipe = new Recipe();

        // Basic fields
        recipe.setTitle(request.getParameter("title"));
        recipe.setDescription(request.getParameter("description"));
        recipe.setTimeInMinutes(Integer.parseInt(request.getParameter("time")));
        recipe.setImgSrc(request.getParameter("imgSrc"));
        recipe.setLanguage("ES");

        // Get the user.
        AppUser loggedUser = userService.getLoggedUser(request);
        recipe.setAuthor(loggedUser);

        // Flags
        try {
            List<String> flags = Arrays.asList(request.getParameterValues("flag"));
            Collection<Flag> flagList = new ArrayList<>();

            flags.forEach(flagID -> {
                flagList.add(flagService.getFlag(Long.parseLong(flagID)));
            });
            recipe.setRecipeFlags(flagList);
        } catch (NullPointerException e) {
            log.info("No flags were found.");
        }
        // Save the recipe
        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        try {
            // Ingredients
            List<String> ingredients = Arrays.asList(request.getParameterValues("ingredient"));
            List<String> quantity = Arrays.asList(request.getParameterValues("quantity"));

            ingredients.forEach(ingID -> {
                ProductType pt = productTypeService.getProductType(Long.parseLong(ingID));
                Ingredient ingredient = new Ingredient(
                        new IngredientId(recipe.getId(), pt.getId()),
                        savedRecipe, pt,
                        Integer.parseInt(quantity.get(ingredients.indexOf(ingID)))
                );
                ingredientService.saveIngredient(ingredient);
            });
        } catch (NullPointerException e) {
            log.info("No ingredients were found.");
        }

        try {
            // Steps
            List<String> steps = Arrays.asList(request.getParameterValues("step"));
            steps.forEach(stepValue -> {
                Step step = new Step(
                        steps.indexOf(stepValue),
                        stepValue, savedRecipe
                );
                stepService.insertStep(step);
            });
        } catch (NullPointerException e) {
            log.info("No steps were found");
        }
        return "redirect:/recipe/view/" + savedRecipe.getId();
    }

    @GetMapping("/view")
    public String getView(
            Model model,
            @RequestParam(value = "page") Optional<Integer> pageNum,
            @RequestParam(value = "page-move", required = false) String direction,
            @RequestParam (required = false) List<String> flags,
            @RequestParam (required = false) String keyword
    ) {
        Integer nextPage;
        List<Recipe> recipeList;

        if (keyword == null) {
            keyword = "";
        }
        if (flags != null && !flags.isEmpty()){
            //Filtrar con flags
            List<Flag> flagObjectList = new ArrayList<>();
            flags.forEach(flagId -> flagObjectList.add(new Flag(Long.parseLong(flagId))));
            nextPage = PageManager.getPageNum(pageNum.orElse(null), (int)
                    recipeService.countPages(flagObjectList, keyword), direction);
            recipeList = recipeService.getAllRecipesByFlagsWithTitleContaining(nextPage - 1,
                    flagObjectList, keyword);
        } else {
            // No filters are present
            nextPage = PageManager.getPageNum(pageNum.orElse(null), (int) recipeService.countPages(keyword), direction);
            recipeList = recipeService.getAllRecipesWithTitleContaining(nextPage - 1, keyword);
        }
        model.addAttribute("keyword", keyword);
        model.addAttribute("recipeList", recipeList);
        model.addAttribute("flagList", flagService.getAllFlags());
        model.addAttribute("selectedFlags", flags != null ? flags : new ArrayList<Flag>());
        model.addAttribute("page", nextPage);

        log.info("Sending user to recipe list view");

        return "recipe/recipe_list";
    }

    @GetMapping("view/{recipeId}")
    public String getRecipe(@PathVariable("recipeId") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);

        log.info("Sending user to recipe {}", id);
        model.addAttribute("recipe", recipe);
        model.addAttribute("steps", stepService.getStepsByRecipe(recipe));

        return "recipe/recipe_view";
    }

    @GetMapping("view/user/{recipeAuthor}")
    public String getRecipesByAuthor(@PathVariable("recipeAuthor") Long id, Model model){
        AppUser author = userService.getUser(id);

        log.info("Sending to recipe list by user {}", id);
        model.addAttribute("recipeList", recipeService.getRecipesByAuthor(author));

        return "recipe/recipe_personal_list";
    }


    @GetMapping("view/modify")
    public String getModifyRecipesByAuthor(Model model, HttpServletRequest request){
        try {
            AppUser author = userService.getLoggedUser(request);
            if (author == null) {
                throw new NullPointerException("Author is null");
            }
            log.info("Sending user {} to its recipe list", author.getId());
            model.addAttribute("recipeList", recipeService.getRecipesByAuthor(author));
        } catch (NullPointerException e) {
            log.info("{}, redirecting to recipe list", e.getMessage());
            return "recipe/recipe_list";
        }
        return "recipe/recipe_modify_list";
    }

    @GetMapping("modify/{recipeId}")
    public String editRecipe(@PathVariable("recipeId") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);

        log.info("Sending user to modify recipe {}", id);
        model.addAttribute("flagList", flagService.getAllFlags());
        model.addAttribute("recipeIngredients", recipe.getIngredientList());
        model.addAttribute("ingredientList", productTypeService.getAllProductTypes());
        model.addAttribute("measurementList", productService.getMeasurementUnits());

        List <Long> selectedFlagList = new ArrayList<>();
        recipe.getRecipeFlags().forEach(flag -> selectedFlagList.add(flag.getId()));
        model.addAttribute("selectedFlagList", selectedFlagList);
        model.addAttribute("recipe", recipe);
        model.addAttribute("steps", stepService.getStepsByRecipe(recipe));

        return "recipe/recipe_modify_view";
    }

    @PostMapping("delete/{recipeId}")
    public void removeRecipe (@PathVariable("recipeId") Long id, HttpServletResponse response) throws IOException {
        Recipe recipe = recipeService.getRecipe(id);

        log.info("Deleting recipe {}", id);
        ingredientService.deleteRecipeIngredients(recipe);
        stepService.deleteRecipeSteps(recipe);
        recipeService.removeRecipe(id);

        log.info("Redirecting to recipe list");
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(response.encodeRedirectURL("/recipe/view/modify"));
    }

}
