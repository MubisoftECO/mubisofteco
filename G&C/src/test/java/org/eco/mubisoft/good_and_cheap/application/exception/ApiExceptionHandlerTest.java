package org.eco.mubisoft.good_and_cheap.application.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.servlet.RequestDispatcher;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleError() {
        try {
            mockMvc.perform(post("/error")
                            .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.FORBIDDEN.value()))
                    .andExpect(redirectedUrl("/problem/403"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isFound());

            mockMvc.perform(post("/error")
                            .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value()))
                    .andExpect(redirectedUrl("/problem/404"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isFound());

            mockMvc.perform(post("/error")
                            .requestAttr(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .andExpect(redirectedUrl("/problem/500"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isFound());

            mockMvc.perform(post("/error"))
                    .andExpect(redirectedUrl("/problem"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getErrorPage() {
        Arrays.asList("403", "404", "500", "600").forEach(errorID -> {
            try {
                mockMvc.perform(get("/problem/" + errorID)
                                .flashAttr("status", "statusTest")
                                .flashAttr("path", "/api/test")
                                .flashAttr("error", "test-error")
                                .flashAttr("path", "test-error-path")
                        )
                        .andDo(MockMvcResultHandlers.log())
                        .andExpect(status().isOk());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}