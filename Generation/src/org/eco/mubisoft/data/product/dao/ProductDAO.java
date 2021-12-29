package org.eco.mubisoft.data.product.dao;

import org.eco.mubisoft.data.product.model.Product;
import org.eco.mubisoft.data.product.model.ProductFamily;
import org.eco.mubisoft.data.product.model.ProductType;

import java.util.List;

public interface ProductDAO {

    void insertProductFamilies(List<ProductFamily> productFamilyList);
    List<ProductFamily> getProductFamilies();
    void deleteProductFamilies();
    void insertProductTypes(List<ProductType> productTypeList);
    List<ProductType> getProductTypes();
    void deleteProductTypes();
    void insertProduct(Product product);
    void deleteProducts();
    long getProductID();
}
