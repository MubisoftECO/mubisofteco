package org.eco.mubisoft.good_and_cheap.product.domain.service;

import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts(Integer pageNum);
    List<String> getMeasurementUnits();
    Product getProduct(Long id);
    void addProduct(Product product);
    boolean removeProduct(Long id);
    double countPages();
    List<ProductType> getIngredients();
}
