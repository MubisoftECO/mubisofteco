package org.eco.mubisoft.good_and_cheap.product.domain.service;

import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(Long id);
    void addProduct(Product product);

    boolean removeProduct(Long id);
}
