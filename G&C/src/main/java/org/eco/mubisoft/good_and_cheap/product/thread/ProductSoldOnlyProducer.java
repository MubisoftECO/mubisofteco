package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;

import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;

import java.util.List;


@Slf4j
public class ProductSoldOnlyProducer implements Runnable {
    private String city;
    private ProductService productService;

    public ProductSoldOnlyProducer(String city, ProductService productService) {
        this.city = city;
        this.productService = productService;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        log.info("(TASK STARTS) PRODUCT (SOLD-ONLY) INFORMATION from DB to LIST {}", Thread.currentThread().getName());
        List<ProductSoldOnlyDto> list = productService.getProductsSoldOnlyInformationFromDB(city);
        list.forEach(p-> productService.setProductsSoldOnlyInformationToBuffer(p));
        log.info("(TASK ENDS) PRODUCT (SOLD-ONLY) INFORMATION from DB to LIST {}", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end -start));
    }
}
