package org.eco.mubisoft.good_and_cheap.recipe.control;

import org.eco.mubisoft.good_and_cheap.application.data.MilliTime;
import org.eco.mubisoft.good_and_cheap.application.security.TokenService;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.recipe.domain.service.RecipeService;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    @Test
    void createRecipe() {
        try {
            mockMvc.perform(get("/recipe/create"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRecipeCRUD() {
        // 1. Create a recipe and get its ID.
        Long recipeID = saveRecipe();

        // 2. Get recipe from database.
        Recipe recipe = recipeService.getRecipe(recipeID);
        assertNotNull(recipe);
        assertEquals(recipeID, recipe.getId());

        // 3. Get the view of a recipe.
        Long databaseRecipe = recipeService.getAllRecipes(1).get(1).getId();
        getRecipe(databaseRecipe);

        // Get the update view of the recipe.
        editRecipe(databaseRecipe);

        // 5. Delete recipe from database.
        removeRecipe(recipe);
    }

    private Long saveRecipe() {
        try {
            // 1. Create a testing user.
            String token = "Bearer " + new TokenService().generateToken(
                    new User("user", "password",
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))),
                    "/recipe/save", MilliTime.FIVE_MINUTES.getTime()
            );
            // 2. Create a new recipe.
            ResultActions result = mockMvc.perform(post("/recipe/save")
                            .param("title", "recipe-test")
                            .param("description", "description-test")
                            .param("time", "1")
                            .param("imgSrc", "test-image.png")
                            .sessionAttr("accessToken", token)
                    )
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(redirectedUrlPattern("/recipe/view/**"))
                    .andExpect(status().isFound());
            // 3. Return the ID of the recipe.
            return Long.valueOf(
                    Objects.requireNonNull(
                            result.andReturn().getResponse().getRedirectedUrl()
                    ).substring("/recipe/view/".length()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void editRecipe(Long recipeID) {
        try {
            mockMvc.perform(get("/recipe/modify/" + recipeID))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_modify_view"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeRecipe(Recipe recipe) {
        try {
            mockMvc.perform(post("/recipe/delete/" + recipe.getId()))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(redirectedUrl("/recipe/view/modify"))
                    .andExpect(status().isFound());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getView() {
        try {
            mockMvc.perform(get("/recipe/view"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_list"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRecipe(Long recipeID) {
        try {
            mockMvc.perform(get("/recipe/view/" + recipeID))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_view"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRecipesByAuthor() {
        Long authorID = userService.getAllUsers().get(0).getId();
        try {
            mockMvc.perform(get("/recipe/view/user/" + authorID))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_personal_list"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getModifyRecipesByAuthor() {
        try {
            mockMvc.perform(get("/recipe/view/modify"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_list"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}