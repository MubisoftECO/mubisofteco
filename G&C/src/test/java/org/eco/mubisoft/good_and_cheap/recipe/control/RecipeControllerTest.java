package org.eco.mubisoft.good_and_cheap.recipe.control;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    void saveRecipe() {
    }

    @Test
    void getView() {
        try {
            mockMvc.perform(get("/recipe/view"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/recipe/recipe_list"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getRecipe() {
    }

    @Test
    void getRecipesByAuthor() {
    }

    @Test
    void getModifyRecipesByAuthor() {
    }

    @Test
    void editRecipe() {
    }

    @Test
    void removeRecipe() {
    }

}