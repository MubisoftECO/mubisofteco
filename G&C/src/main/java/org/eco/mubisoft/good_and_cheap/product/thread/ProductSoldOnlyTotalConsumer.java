package org.eco.mubisoft.good_and_cheap.product.thread;


import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@Component
public class ProductSoldOnlyTotalConsumer implements Callable<List<MostLessSoldDetail>> {
    private ProductService productService;

    public ProductSoldOnlyTotalConsumer(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public List<MostLessSoldDetail> call() throws Exception {
        List<MostLessSoldDetail> list = null;

        list = productService.getProductsSoldOnlyTotalListFromBuffer();
        Collections.sort(list, (t1, t2) -> t2.getTotal().compareTo(t1.getTotal()));
        return list;
    }
}
