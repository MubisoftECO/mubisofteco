package org.eco.mubisoft.good_and_cheap.thread;

import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductService;
import org.eco.mubisoft.good_and_cheap.product.thread.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ThreadInitializer {

    private static final List<Runnable> listProductType = new ArrayList<>();

    private ThreadInitializer() {
    }

    public static void setTypeRunnableList(Long id, ProductService productService) {
        listProductType.add(new ProductTypeExpiredProducer(id,productService));
        listProductType.add(new ProductTypeOtherProducer(id,productService));
        listProductType.add(new ProductTypeSoldProducer(id,productService));
    }

    public static List<Runnable> getListProductType() {
        return listProductType;
    }

}
