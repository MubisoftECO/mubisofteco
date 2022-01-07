package org.eco.mubisoft.good_and_cheap.recipe.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.security.TokenChecker;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.FlagService;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.RecipeService;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.StepService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;
    private final FlagService flagService;
    private final StepService stepService;

    @GetMapping("/create")
    public String createProduct(Model model){
        model.addAttribute("pageTitle", "recipe_form");
        model.addAttribute("flagList", flagService.getAllFlags());
        return "recipe/recipe_form";
    }

    @PostMapping("/save")
    public String saveRecipe(HttpServletRequest request, HttpServletResponse response){
        Recipe recipe = new Recipe();

        recipe.setTitle(request.getParameter("title"));

        recipe.setDescription(request.getParameter("description"));

        recipe.setTimeInMinutes(Integer.parseInt(request.getParameter("timeInMinutes")));

        /*recipe.setIngredients(request.getParameter("ingredients"));*/
        /*flags*/

        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        TokenChecker tokenChecker = new TokenChecker();
        String username = tokenChecker.getUsernameFromToken(accessToken);
        AppUser loggedUser = userService.getUser(username);
        recipe.setAuthor(loggedUser);

        recipe.setImgSrc(request.getParameter("imgSrc"));

        recipeService.saveRecipe(recipe);
       // return "redirect:/recipe/view/"+recipe.getId().toString();
        return "redirect:/recipe/view/";
    }

    @GetMapping("/view")
    public String getView(Model model){
        List<Recipe> recipeList = recipeService.getAllRecipes();
        model.addAttribute("recipeList", recipeList);
        return "recipe/recipe_list";
}

    @GetMapping("view/{recipeId}")
    public String getRecipe(@PathVariable("recipeId") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);
        model.addAttribute("recipe", recipe);
        /*Hay que ordenar la lista*/
        model.addAttribute("steps", stepService.getStepsByRecipe(recipe));
        return "recipe/recipe_view";
    }

    @GetMapping("view/user/{recipeAuthor}")
    public String getRecipesByAuthor(@PathVariable("recipeAuthor") Long id, Model model){
        AppUser author = userService.getUser(id);
        model.addAttribute("recipeList", recipeService.getRecipesByAuthor(author));
        return "recipe/recipe_personal_list";
    }

}
