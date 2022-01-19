package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Component
public class ProductSoldOnlyTotalConsumer implements Callable<List<MostLessSoldDetail>> {
    private ProductService productService;

    public ProductSoldOnlyTotalConsumer(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public List<MostLessSoldDetail> call() throws Exception {
        List<MostLessSoldDetail> list = null;
        long start = System.currentTimeMillis();
        log.info("(TASK STARTS) PRODUCT SOLD from BUFFER to LIST {}", Thread.currentThread().getName());

        list = productService.getProductsSoldOnlyTotalListFromBuffer();
        Collections.sort(list, (t1, t2) -> t2.getTotal().compareTo(t1.getTotal()));

        log.info("(TASK ENDS) PRODUCT SOLD from BUFFER to LIST {}", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end -start));
        return list;
    }
}
