package org.eco.mubisoft.good_and_cheap.product.thread;


import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;

import java.util.List;



public class ProductSoldOnlyProducer implements Runnable {
    private String city;
    private ProductService productService;

    public ProductSoldOnlyProducer(String city, ProductService productService) {
        this.city = city;
        this.productService = productService;
    }

    @Override
    public void run() {

        List<ProductSoldOnlyDto> list = productService.getProductsSoldOnlyInformationFromDB(city);
        list.forEach(p-> productService.setProductsSoldOnlyInformationToBuffer(p));
    }
}
