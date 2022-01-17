package org.eco.mubisoft.generator.data.product.domain.service;

import org.eco.mubisoft.generator.data.product.domain.model.ProductFamily;
import org.eco.mubisoft.generator.data.product.domain.model.ProductType;

import java.util.List;

public interface ProductService {

    void generateProductTypes();
    void generateProductFamilies();
    void generateProducts(int quantity);
    List<ProductType> getProductTypes();
    List<ProductFamily> getProductFamilies();
    void deleteAll();

}
