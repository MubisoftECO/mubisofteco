package org.eco.mubisoft.good_and_cheap.product.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.repo.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceFacade implements ProductService {

    private static final int ELEMENT_NUM = 20;
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts(Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, ELEMENT_NUM);
        return productRepository.findAll(pageable).toList();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public boolean removeProduct(Long id) {
        if(id == null ) return false;
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public double countPages() {
        return Math.ceil(productRepository.count() / ELEMENT_NUM);
    }


}
