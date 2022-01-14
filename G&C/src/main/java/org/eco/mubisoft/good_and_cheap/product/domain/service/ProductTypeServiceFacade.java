package org.eco.mubisoft.good_and_cheap.product.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.domain.repo.ProductTypeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductTypeServiceFacade implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;


    @Override
    public List<ProductType> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    @Override
    public ProductType getProductType(Long id) {

        return productTypeRepository.findById(id).orElse(null);
    }
}
