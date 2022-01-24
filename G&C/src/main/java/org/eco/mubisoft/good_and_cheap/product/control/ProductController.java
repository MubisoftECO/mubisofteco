package org.eco.mubisoft.good_and_cheap.product.control;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.pages.PageManager;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductTypeService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/product")
public class ProductController {

    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final UserService userService;

    @GetMapping("/create")
    public String createProduct(Model model) {
        Product product = new Product();

        model.addAttribute("productTypeList", productTypeService.getAllProductTypes());
        model.addAttribute("product", product);
        log.info("Sending to product create form");

        return "/product/product_form";
    }

    @PostMapping("/save")
    public String saveProduct(
            HttpServletRequest request,
            @RequestParam(name = "date") String date
    ) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Product product = new Product();

        // Set product fields
        product.setNameEn(request.getParameter("name"));
        product.setQuantity(Double.parseDouble(request.getParameter("quantity")));
        product.setPrice(Double.parseDouble(request.getParameter("price")));

        // Set dates
        product.setExpirationDate(format.parse(date));
        product.setPublishDate(new Date());

        // Set foreign fields
        product.setVendor(userService.getLoggedUser(request));
        product.setProductType(productTypeService.getProductType(
                Long.parseLong(request.getParameter("productType"))
        ));
        Product newProduct = productService.addProduct(product);
        log.info("Creating product with id={}", newProduct.getId());

        return "redirect:/product/view/" + newProduct.getId().toString();
    }

    @GetMapping("/back")
    public String backProduct() {
        log.info("Redirecting to index");
        return "redirect:/";
    }

    @GetMapping("/view")
    public String productList(
            Model model,
            @RequestParam(value = "page") Optional<Integer> pageNum,
            @RequestParam(value = "page-move", required = false) String direction
            )
    {
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
    public String showUpdateProduct(Model model, @PathVariable(value = "product_id") long id) {
        Product product = productService.getProduct(id);
        model.addAttribute("product", product);

        return "/product/product_form";
    }

    @PostMapping("/delete/{product_id}")
    public String deleteProduct(@PathVariable(value = "product_id") long id) {
        log.info("Deleting product with id={}", id);
        productService.removeProduct(id);
        return "redirect:/product/view";
    }

    @GetMapping("/view/modify")
    public String getModifyProductByVendor(Model model, HttpServletRequest request,
                                           @RequestParam(value = "page") Optional<Integer> pageNum,
                                           @RequestParam(value = "page-move", required = false) String direction){
        AppUser vendor = userService.getLoggedUser(request);
        Integer nextPage = PageManager.getPageNum(pageNum.orElse(null), (int) productService.countPages(vendor), direction);
        model.addAttribute("page", nextPage);
        model.addAttribute("productList", productService.getProductByVendor(vendor, nextPage - 1));

        return "/product/product_personal_list";
    }

}
