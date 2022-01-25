package org.eco.mubisoft.good_and_cheap.recipe.control;

import org.eco.mubisoft.good_and_cheap.recipe.domain.service.FlagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class FlagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlagService flagService;

    @Test
    void setFlagForm() {
        try {
            mockMvc.perform(get("/flag/create"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/recipe/flags_form"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void setRecipeFlag() {
        try {
            String flagID = mockMvc.perform(post("/flag/save")
                            .param("name", "flag-test")
                    )
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            flagService.deleteFlag(flagService.getFlag(Long.valueOf(flagID)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}