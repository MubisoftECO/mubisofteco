package org.eco.mubisoft.good_and_cheap.product.thread;


import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;


@Component
public class ProductSoldOnlyConsumer implements Callable<List<ProductSoldOnlyDto>> {
    private ProductService productService;

    public ProductSoldOnlyConsumer(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public List<ProductSoldOnlyDto> call() throws Exception {
        List<ProductSoldOnlyDto> list = null;
        list = productService.getProductsSoldOnlyListFromBuffer();
        Thread.sleep(1000);
        return list;
    }
}
