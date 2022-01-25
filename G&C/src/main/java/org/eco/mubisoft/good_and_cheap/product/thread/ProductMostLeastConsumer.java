package org.eco.mubisoft.good_and_cheap.product.thread;

import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;

import java.util.List;
import java.util.concurrent.Callable;


public class ProductMostLeastConsumer implements Callable<List<MostLeastSold>> {
    private ProductService productService;

    public ProductMostLeastConsumer(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<MostLeastSold> call() throws Exception {
        List<MostLeastSold> list = null;
        list = productService.getProductsMostSoldListFromBuffer();
        Thread.sleep(100);
        return list;
    }
}
