package org.eco.mubisoft.good_and_cheap.recipe.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.pages.PageManager;
import org.eco.mubisoft.good_and_cheap.application.security.TokenService;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Flag;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.FlagService;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.RecipeService;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.StepService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/recipe")
public class RecipeController {

    private final ProductService productService;
    private final RecipeService recipeService;
    private final UserService userService;
    private final FlagService flagService;
    private final StepService stepService;

    @GetMapping("/create")
    public String createRecipe(Model model){
        model.addAttribute("flagList", flagService.getAllFlags());
        model.addAttribute("ingredientList", productService.getIngredients());
        model.addAttribute("measurementList", productService.getMeasurementUnits());

        return "recipe/recipe_form";
    }

    @PostMapping("/save")
    public String saveRecipe(HttpServletRequest request, HttpServletResponse response){
        Recipe recipe = new Recipe();

        recipe.setTitle(request.getParameter("title"));
        recipe.setDescription(request.getParameter("description"));
        recipe.setTimeInMinutes(Integer.parseInt(request.getParameter("timeInMinutes")));

        HttpSession session = request.getSession();
        String accessToken = (String) session.getAttribute("accessToken");
        TokenService tokenService = new TokenService();
        String username = tokenService.getUsernameFromToken(accessToken);
        AppUser loggedUser = userService.getUser(username);
        recipe.setAuthor(loggedUser);

        recipe.setImgSrc(request.getParameter("imgSrc"));

        recipeService.saveRecipe(recipe);


        return "redirect:/recipe/view/";
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

        if(keyword == null) keyword = "";

        if(flags != null && !flags.isEmpty()){
            //Filtrar con flags
            List<Flag> flagObjectList = new ArrayList<>();
            flags.forEach(flagId -> flagObjectList.add(new Flag(Long.parseLong(flagId))));
            nextPage = PageManager.getPageNum(pageNum.orElse(null), (int)
                    recipeService.countPages(flagObjectList, keyword), direction);
            recipeList = recipeService.getAllRecipesByFlagsWithTitleContaining(nextPage - 1,
                    flagObjectList, keyword);
        } else {
            //Si no hay filtros
            nextPage = PageManager.getPageNum(pageNum.orElse(null), (int) recipeService.countPages(keyword), direction);
            recipeList = recipeService.getAllRecipesWithTitleContaining(nextPage - 1, keyword);
        }


        model.addAttribute("keyword", keyword);
        model.addAttribute("recipeList", recipeList);
        model.addAttribute("flagList", flagService.getAllFlags());
        model.addAttribute("selectedFlags", flags != null ? flags : new ArrayList<Flag>());
        model.addAttribute("page", nextPage);

        return "recipe/recipe_list";
    }

    @GetMapping("view/{recipeId}")
    public String getRecipe(@PathVariable("recipeId") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);
        model.addAttribute("recipe", recipe);
        /* Hay que ordenar la lista */
        model.addAttribute("steps", stepService.getStepsByRecipe(recipe));
        return "recipe/recipe_view";
    }

    @GetMapping("view/user/{recipeAuthor}")
    public String getRecipesByAuthor(@PathVariable("recipeAuthor") Long id, Model model){
        AppUser author = userService.getUser(id);
        model.addAttribute("recipeList", recipeService.getRecipesByAuthor(author));
        return "recipe/recipe_personal_list";
    }


    @GetMapping("view/modify")
    public String getModifyRecipesByAuthor(Model model, HttpServletRequest request){
        AppUser author = userService.getLoggedUser(request);
        model.addAttribute("recipeList", recipeService.getRecipesByAuthor(author));
        return "recipe/recipe_modify_list";
    }

    @GetMapping("modify/{recipeId}")
    public String editRecipe(@PathVariable("recipeId") Long id, Model model) {
        Recipe recipe = recipeService.getRecipe(id);
        model.addAttribute("flagList", flagService.getAllFlags());
        model.addAttribute("ingredientList", productService.getIngredients());
        model.addAttribute("measurementList", productService.getMeasurementUnits());
        List <Long> selectedFlagList = new ArrayList<>();
        recipe.getRecipeFlags().forEach(flag -> selectedFlagList.add(flag.getId()));
        model.addAttribute("selectedFlagList", selectedFlagList);
        model.addAttribute("recipe", recipe);
        /* Hay que ordenar la lista */
        model.addAttribute("steps", stepService.getStepsByRecipe(recipe));
        return "recipe/recipe_modify_view";
    }

    @PostMapping("delete/{recipeId}")
    public void removeRecipe (@PathVariable("recipeId") Long id, HttpServletResponse response) throws IOException {
        stepService.deleteRecipeSteps(new Recipe(id));
        recipeService.removeRecipe(id);
        response.setStatus(HttpServletResponse.SC_OK);
        response.sendRedirect(response.encodeRedirectURL("/recipe/view/modify"));
    }
}
