package org.eco.mubisoft.good_and_cheap;

import org.eco.mubisoft.good_and_cheap.user.control.controller.UserController;
import org.eco.mubisoft.good_and_cheap.user.domain.service.RoleService;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p><b>Application Tests</b></p>
 * <p>Tests for the main application.</p>
 */
@WebMvcTest(UserController.class)
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Test
    void saveUserTest () throws Exception {
        mockMvc.perform(post("/user/save")
                .param("name", "Test")
                .param("secondName", "Test")
                .param("username", "userTest")
                .param("password", "testPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/")).andExpect(status().isFound());
    }

}
