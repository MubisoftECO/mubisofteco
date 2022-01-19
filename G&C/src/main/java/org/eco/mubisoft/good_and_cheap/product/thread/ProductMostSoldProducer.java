package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSoldType;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductMostSoldProducer implements Runnable {
    private ProductService productService;
    List<MostLessSoldDetail> productMostLessSoldDetailList;

    public ProductMostSoldProducer(ProductService productService, List<MostLessSoldDetail> productMostLessSoldDetailList) {
        this.productService = productService;
        this.productMostLessSoldDetailList = productMostLessSoldDetailList;
    }

    @Override
    public void run() {
        List<MostLessSoldDetail> list = new ArrayList<>();
        List<MostLeastSold>  topList = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();

        int sizeList = productMostLessSoldDetailList.size();

        if(sizeList < 5) {
            list = productMostLessSoldDetailList.subList(0,sizeList);
        } else {
            list = productMostLessSoldDetailList.subList(0,5);
        }
        list.forEach(p-> topList.add(new MostLeastSold(i.getAndIncrement(),p.getName_en(),p.getName_eu(), p.getName_es(),p.getTotal(), MostLeastSoldType.TOP)));

       topList.forEach(m-> productService.setProductsMostSoldInformationToBuffer(m));
    }
}
