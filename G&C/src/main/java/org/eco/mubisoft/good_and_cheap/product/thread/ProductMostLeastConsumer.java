package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class ProductMostLeastConsumer implements Callable<List<MostLeastSold>> {
    private ProductService productService;

    public ProductMostLeastConsumer(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<MostLeastSold> call() throws Exception {
        List<MostLeastSold> list = null;
        long start = System.currentTimeMillis();
        log.info("(TASK STARTS) PRODUCT SOLD from BUFFER to LIST {}", Thread.currentThread().getName());

        list = productService.getProductsMostSoldListFromBuffer();

        log.info("(TASK ENDS) PRODUCT SOLD from BUFFER to LIST {}", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end -start));
        Thread.sleep(100);
        return list;
    }
}
