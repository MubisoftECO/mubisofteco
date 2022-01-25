package org.eco.mubisoft.good_and_cheap.product.control;

import org.eco.mubisoft.good_and_cheap.application.data.MilliTime;
import org.eco.mubisoft.good_and_cheap.application.security.TokenService;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
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
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void before() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        try {
            // Get the data from the restricted list.
            mockMvc.perform(get("/product/create"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isForbidden());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testProductCRUD() {
        // 1. Create product and get its ID.
        Long productID = Long.parseLong(Objects.requireNonNull(saveProduct()));

        // 2. Get the product from the database.
        Product product = productService.getProduct(productID);
        assertNotNull(product);
        assertEquals(productID, product.getId());

        // 3. Get the view for the product.
        viewProduct(product);

        // 4. Get the update view for the product.
        showUpdateProduct(product);

        // 5. Delete the product from the database.
        deleteProduct(product);
    }

    private String saveProduct() {
        try {
            // 1. Create a test user
            String token = "Bearer " + new TokenService().generateToken(
                    new User("vendedor.ejemplo", "password",
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_VENDOR"))),
                    "/recipe/save", MilliTime.FIVE_MINUTES.getTime()
            );
            // 2. Get the first ingredient on the table
            ProductType productType = productService.getIngredients().get(0);
            // 3. Save the product
            ResultActions result = mockMvc.perform(post("/product/save")
                            .param("date", "0000-00-00")
                            .param("name", "test-product")
                            .param("quantity", "1")
                            .param("price", "1")
                            .param("productType", productType.getId().toString())
                            .sessionAttr("accessToken", token)
                    )
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(redirectedUrlPattern("/product/view/**"))
                    .andExpect(status().isFound());
            // 4. Return the product ID
            return Objects.requireNonNull(
                            result.andReturn().getResponse().getRedirectedUrl()
                    ).substring("/product/view/".length());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteProduct(Product product) {
        try {
            mockMvc.perform(post("/product/delete/" + product.getId()))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(redirectedUrl("/product/view"))
                    .andExpect(status().isFound());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void backProduct() {
        try {
            mockMvc.perform(get("/product/back"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(redirectedUrl("/"))
                    .andExpect(status().isFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void productList() {
        try {
            // Test without parameters.
            mockMvc.perform(get("/product/view"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/product/product_list"));

            // Test with parameters.
            mockMvc.perform(get("/product/view")
                            .param("page", "2")
                            .param("page-move", "next")
                    )
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/product/product_list"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void viewProduct(Product product) {
        try {
            mockMvc.perform(get("/product/view/" + product.getId()))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/product/product_current"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showUpdateProduct(Product product) {
        try {
            mockMvc.perform(get("/product/edit/" + product.getId()))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/product/product_form"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getModifyProductByVendor() {
        try {
            mockMvc.perform(get("/product/view/modify"))
                    .andDo(MockMvcResultHandlers.log())
                    .andExpect(status().isOk())
                    .andExpect(view().name("/product/product_personal_list"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}