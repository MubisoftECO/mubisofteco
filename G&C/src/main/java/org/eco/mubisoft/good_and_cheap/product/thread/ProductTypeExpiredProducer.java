package org.eco.mubisoft.good_and_cheap.product.thread;

import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;

import java.util.List;


public class ProductTypeExpiredProducer implements Runnable {

    private static final String REASON = "EXPIRED";

    private final ProductService productService;
    private final Long id;

    public ProductTypeExpiredProducer(Long id, ProductService productService) {
        this.id = id;
        this.productService = productService;
    }

    @Override
    public void run() {
        List<ProductDto> list = productService.getProductsInformationFromDB(id, REASON);
        list.forEach(productService::setProductsInformationToBuffer);
    }
}
