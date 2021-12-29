package org.eco.mubisoft.good_and_cheap.product.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "/product/product_form";
    }

    @PostMapping("/save")
    public String saveProduct(@RequestParam(name = "date") String date, Product product) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date expirationDate = format.parse(date);
            product.setExpirationDate(expirationDate);
            Date publishDate = new Date();
            productService.addProduct(product);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "redirect:/product/view/"+product.getId().toString();
    }

    @PostMapping("/back")
    public String backProduct() {

        return "redirect:/";
    }

    @GetMapping("/view")
    public String productList(Model model) {
        List<Product> list = productService.getAllProducts();
        model.addAttribute("productList", list);

        return "/product/product_list";
    }


    @GetMapping("/view/{product_id}")
    public String viewProduct(Model model, @PathVariable(value = "product_id") long id) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);

        return "/product/product_current";
    }


    @GetMapping("/edit/{product_id}")
    public String ShowUpdateProduct(Model model, @PathVariable(value = "product_id") long id) {
        Product product = productService.getProduct(id);

        model.addAttribute("product", product);

        return "/product/product_form";
    }

    @GetMapping("/delete/{product_id}")
    public String deleteProduct(Model model, @PathVariable(value = "product_id") long id) {
       productService.removeProduct(id);

        return "/product/product_list";
    }

}
