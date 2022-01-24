package org.eco.mubisoft.good_and_cheap.product.thread;


import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;

import java.util.ArrayList;
import java.util.List;

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
        productSoldOnlyDtoList.forEach(p-> mostLessSoldDetailList.add(new MostLessSoldDetail(p.getNameEn(), p.getNameEs(), p.getNameEu(),(p.getQuantity()* p.getPrice()) )));
        mostLessSoldDetailList.forEach(p-> productService.setProductsSoldOnlyTotalInformationToBuffer(p));
    }
}
