package org.eco.mubisoft.good_and_cheap.product.domain.service;

import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts(Integer pageNum);
    List<String> getMeasurementUnits();
    Product getProduct(Long id);
    void addProduct(Product product);
    boolean removeProduct(Long id);
    double countPages();
    List<ProductType> getIngredients();

    /**
     * <p><b>THREAD</b></p>
     * <p>Get | Set product from DB to buffer and from buffer to list</p>
     */
    List<ProductDto> getProductDtoListFromBuffer();
    List<ProductDto> getProductsInformationFromDB(Long id, String reason);
    void setProductsInformationToBuffer(ProductDto p);
}
