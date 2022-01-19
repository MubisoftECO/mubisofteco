package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;

import java.util.List;

@Slf4j
public class ProductTypeOtherProducer implements  Runnable {
    private final String REASON = "OTHER";
    private Long id;
    private ProductService productService;

    public ProductTypeOtherProducer(Long id, ProductService productService) {
        this.id = id;
        this.productService = productService;
    }

    @Override
    public void run() {
        List<ProductDto> list = productService.getProductsInformationFromDB(id, REASON);
        list.forEach(p-> productService.setProductsInformationToBuffer(p));
    }
}
