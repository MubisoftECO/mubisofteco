package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;

import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Component
public class ProductSoldOnlyConsumer implements Callable<List<ProductSoldOnlyDto>> {
    private ProductService productService;

    public ProductSoldOnlyConsumer(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public List<ProductSoldOnlyDto> call() throws Exception {
        List<ProductSoldOnlyDto> list = null;
        long start = System.currentTimeMillis();
        log.info("(TASK STARTS) PRODUCT SOLD from BUFFER to LIST {}", Thread.currentThread().getName());

        list = productService.getProductsSoldOnlyListFromBuffer();

        log.info("(TASK ENDS) PRODUCT SOLD from BUFFER to LIST {}", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end -start));
        Thread.sleep(1000);
        return list;
    }
}
