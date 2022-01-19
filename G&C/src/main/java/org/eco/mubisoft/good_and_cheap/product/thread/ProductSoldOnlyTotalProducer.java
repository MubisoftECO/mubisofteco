package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;

import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductSoldOnlyTotalProducer implements Runnable {

    private ProductService productService;
    private List<ProductSoldOnlyDto> productSoldOnlyDtoList;
    List<MostLessSoldDetail> mostLessSoldDetailList;

    public ProductSoldOnlyTotalProducer(ProductService productService, List<ProductSoldOnlyDto> productSoldOnlyDtoList) {
        this.productService = productService;
        this.productSoldOnlyDtoList = productSoldOnlyDtoList;
        this.mostLessSoldDetailList = new ArrayList<>();
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        log.info("(TASK STARTS) PRODUCT (SOLD-CITY) INFORMATION from DB to LIST {}", Thread.currentThread().getName());

        productSoldOnlyDtoList.forEach(p-> mostLessSoldDetailList.add(new MostLessSoldDetail(p.getNameEn(), p.getNameEs(), p.getNameEu(),(p.getQuantity()* p.getPrice()) )));
        mostLessSoldDetailList.forEach(p-> productService.setProductsSoldOnlyTotalInformationToBuffer(p));

        log.info("(TASK ENDS) PRODUCT (SOLD-CITY) INFORMATION from DB to LIST {}", Thread.currentThread().getName());
        long end = System.currentTimeMillis();
        log.info("Total time {}", (end -start));
    }
}
