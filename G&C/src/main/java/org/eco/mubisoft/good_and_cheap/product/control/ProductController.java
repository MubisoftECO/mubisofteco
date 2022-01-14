package org.eco.mubisoft.good_and_cheap.product.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.pages.PageManager;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.Option;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;
    private final ProductTypeService productTypeService;

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();
        List<ProductType> productTypeList = productTypeService.getAllProductTypes();

        model.addAttribute("productTypeList", productTypeList);
        model.addAttribute("product", product);
        return "/product/product_form";
    }

    @PostMapping("/save")
    public String saveProduct(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "date") String date, Product product) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date publishDate = new Date();

        Product producto = new Product();
        producto.setName_es(request.getParameter("name_es"));

        double quantity = Double.parseDouble(request.getParameter("quantity"));
        producto.setQuantity(quantity);
        double price = Double.parseDouble(request.getParameter("price"));
        producto.setPrice(price);

        Date expirationDate = format.parse(date);
        product.setExpirationDate(expirationDate);


        producto.setProductType(productTypeService.getProductType(
            Long.parseLong(request.getParameter("productType"))
        ));
        /*
        Location locationToSave = new Location();
        locationToSave.setCity(cityService.getCity(Long.parseLong(request.getParameter("city"))));
        Location savedLocation = locationService.saveLocation(locationToSave);

        user.setLocation(savedLocation);*/



        productService.addProduct(product);


        return "redirect:/product/view/"+product.getId().toString();
    }

    @PostMapping("/back")
    public String backProduct() {
        return "redirect:/";
    }

    @GetMapping("/view")
    public String productList(
            Model model,
            @RequestParam(value = "page") Optional<Integer> pageNum,
            @RequestParam(value = "page-move", required = false) String direction
            ) {
        Integer nextPage = PageManager.getPageNum(pageNum.orElse(null), (int) productService.countPages(), direction);
        List<Product> list = productService.getAllProducts(nextPage - 1);


        model.addAttribute("productList", list);
        model.addAttribute("page", nextPage);

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
