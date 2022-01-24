package org.eco.mubisoft.good_and_cheap.product.thread;

import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;

import java.util.List;

public class ProductTypeSoldProducer implements Runnable {
    private final String REASON = "SOLD";
    private Long id;
    private ProductService productService;

    public ProductTypeSoldProducer(Long id, ProductService productService) {
        this.id = id;
        this.productService = productService;
    }

    @Override
    public void run() {
        List<ProductDto> list = productService.getProductsInformationFromDB(id, REASON);
        list.forEach(p-> productService.setProductsInformationToBuffer(p));
    }
}
