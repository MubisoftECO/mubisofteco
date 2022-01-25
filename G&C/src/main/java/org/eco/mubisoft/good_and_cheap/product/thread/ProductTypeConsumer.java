package org.eco.mubisoft.good_and_cheap.product.thread;


import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;

import java.util.List;
import java.util.concurrent.Callable;


public class ProductTypeConsumer implements Callable<List<ProductDto>> {
    private ProductService productService;

    public ProductTypeConsumer(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public List<ProductDto> call() throws Exception {
        List<ProductDto> list = null;

        list = productService.getProductDtoListFromBuffer();

        Thread.sleep(1000);
        return list;
    }
}
