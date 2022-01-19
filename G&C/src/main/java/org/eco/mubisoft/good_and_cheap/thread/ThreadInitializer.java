package org.eco.mubisoft.good_and_cheap.thread;

import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.thread.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Component
public class ThreadInitializer {
    static List<Runnable> listProductType;
    static List<Runnable> listProductMostLeast;
    static Future futureProductDto;

    public ThreadInitializer() {
        listProductType = new ArrayList<>();
        listProductMostLeast = new ArrayList<>();
    }

    public static void setTypeRunnableList(Long id, ProductService productService) {
        listProductType.add(new ProductTypeExpiredProducer(id,productService));
        listProductType.add(new ProductTypeOtherProducer(id,productService));
        listProductType.add(new ProductTypeSoldProducer(id,productService));
    }

    public static void  setProductMostLeastList(ProductService productService,  List<MostLessSoldDetail> productMostLessSoldDetailList) {
        listProductMostLeast.add(new ProductMostSoldProducer(productService,productMostLessSoldDetailList));
        listProductMostLeast.add(new ProductLeastSoldProducer(productService,productMostLessSoldDetailList));
    }

    public static List<Runnable> getListProductType() {
        return listProductType;
    }

    public static List<Runnable> getListProductMostLeast() {
        return listProductMostLeast;
    }

    public static void setFutureListProductDto(ProductService productService) {
        Callable ptc = new ProductTypeConsumer(productService);
        futureProductDto = ThreadExecutorService.INSTANCE.getExecutorService().submit(() -> ptc.call());
    }

    public static Future getFutureProductDto() {
        return futureProductDto;
    }
}
