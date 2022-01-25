package org.eco.mubisoft.good_and_cheap.product.domain.service;


import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;

import java.util.List;

public interface ProductTypeService {

    List<ProductType> getAllProductTypes();
    ProductType getProductType(Long id);

}
