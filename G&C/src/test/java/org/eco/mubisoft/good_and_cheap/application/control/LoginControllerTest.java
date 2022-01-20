package org.eco.mubisoft.good_and_cheap.application.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getLoginForm() {
        try {
            // Test without error message.
            mockMvc.perform(get("/login/sign-in"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/login/login_form"));

            // Test with error message.
            mockMvc.perform(get("/login/sign-in" + "User-not-found"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/login/login_form"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void logout() {
        try {
            mockMvc.perform(get("/login/logout"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().is3xxRedirection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}