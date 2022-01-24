package org.eco.mubisoft.good_and_cheap.product.thread;


import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSoldType;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductLeastSoldProducer implements Runnable{
    private ProductService productService;
    List<MostLessSoldDetail> productMostLessSoldDetailList;

    public ProductLeastSoldProducer(ProductService productService, List<MostLessSoldDetail> productMostLessSoldDetailList) {
        this.productService = productService;
        this.productMostLessSoldDetailList = productMostLessSoldDetailList;
    }

    @Override
    public void run() {
        List<MostLessSoldDetail> list = new ArrayList<>();
        List<MostLeastSold>  bottomList = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        int sizeList = productMostLessSoldDetailList.size();

        if(sizeList < 5) {
            list = productMostLessSoldDetailList.subList(0,sizeList);
        } else {
            list = productMostLessSoldDetailList.subList(sizeList-5,sizeList);
        }


        Collections.sort(list, (t1, t2) -> t1.getTotal().compareTo(t2.getTotal()));
        list.forEach(p-> bottomList.add(new MostLeastSold(i.getAndIncrement(),p.getName_en(),p.getName_eu(), p.getName_es(),p.getTotal(), MostLeastSoldType.BOTTOM)));
        bottomList.forEach(m-> productService.setProductsMostSoldInformationToBuffer(m));
    }
}
