package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;

import java.util.List;

@Slf4j
public class ProductTypeExpiredProducer implements Runnable {
    private final String REASON = "EXPIRED";
    private Long id;
    private ProductService productService;

    public ProductTypeExpiredProducer(Long id, ProductService productService) {
        this.id = id;
        this.productService = productService;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        log.info("(TASK STARTS) PRODUCT (EXPIRED) INFORMATION from DB to LIST {}", Thread.currentThread().getName());
        List<ProductDto> list = productService.getProductsInformationFromDB(id, REASON);
        list.forEach(p-> productService.setProductsInformationToBuffer(p));
        log.info("(TASK ENDS) PRODUCT (EXPIRED) INFORMATION from DB to LIST {}", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end -start));
    }
}
