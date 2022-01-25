package org.eco.mubisoft.good_and_cheap.user.control.controller;

import org.eco.mubisoft.good_and_cheap.user.domain.service.AutonomousCommunityService;
import org.eco.mubisoft.good_and_cheap.user.domain.service.ProvinceService;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired private AutonomousCommunityService autonomousCommunityService;
    @Autowired private ProvinceService provinceService;

    @Test
    void createUser() {
        try {
            mockMvc.perform(get("/user/create"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("user/user_form"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getProvincesByAutonomousCommunity() {
        // Get an autonomous community from the database
        Long id = autonomousCommunityService.getAllAutonomousCommunities().get(0).getId();

        // Test method
        try {
            mockMvc.perform(get("/user/create/getProvince/" + id))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getCitiesByProvince() {
        // Get an autonomous community from the database
        Long id = provinceService.getAllProvinces().get(0).getId();

        // Test method
        try {
            mockMvc.perform(get("/user/create/getCity/" + id))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void saveUser() {
    }

    @Test
    void getView() {
        try {
            mockMvc.perform(get("/user/view"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}