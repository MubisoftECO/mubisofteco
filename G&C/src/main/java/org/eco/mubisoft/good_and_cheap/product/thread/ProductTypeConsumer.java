package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class ProductTypeConsumer implements Callable<List<ProductDto>> {
    private ProductService productService;

    public ProductTypeConsumer(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public List<ProductDto> call() throws Exception {
        List<ProductDto> list = null;
        long start = System.currentTimeMillis();
        log.info("(TASK STARTS) PRODUCT from BUFFER to LIST {}", Thread.currentThread().getName());

        list = productService.getProductDtoListFromBuffer();

        log.info("(TASK ENDS) PRODUCT from BUFFER to LIST {}", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end -start));
        Thread.sleep(1000);
        return list;
    }
}
