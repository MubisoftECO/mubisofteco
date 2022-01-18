package org.eco.mubisoft.good_and_cheap.product.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.domain.repo.ProductRepository;
import org.eco.mubisoft.good_and_cheap.product.domain.repo.ProductTypeRepository;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductBuffer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceFacade implements ProductService {

    private static final double ELEMENT_NUM = 20;
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final EntityManager entityManager;
    private final ProductBuffer productBuffer;


    @Override
    public List<Product> getAllProducts(Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, (int) ELEMENT_NUM);
        return productRepository.findAll(pageable).toList();
    }

    @Override
    public List<String> getMeasurementUnits() {
        return productTypeRepository.getAllMeasurementUnits();
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

    @Override
    public List<ProductType> getIngredients() {
        return productTypeRepository.findAll();
    }

    @Override
    public List<ProductDto> getProductDtoListFromBuffer() {
        List<ProductDto> list = new ArrayList<>();
        while (productBuffer.getBufferSize() > 0) {
            list.add(productBuffer.get());
        }
        return list;
    }

    @Override
    public List<ProductDto> getProductsInformationFromDB(Long id, String reason) {
        Query query = entityManager.createNativeQuery("SELECT pt.name_en as 'nameEn', pt.name_es as 'nameEs', pt.name_eu as 'nameEu', SUM(p.price) as 'total', p.remove_reason as 'reason' FROM product p JOIN product_type pt on pt.id = p.product_type_id WHERE (p.vendor_id = ?1) AND (p.remove_reason = ?2) GROUP BY pt.id","ProductDetailsMapping");
        query.setParameter(1, id);
        query.setParameter(2,reason);

        List<ProductDto> list = query.getResultList();

        return list;
    }

    @Override
    public void setProductsInformationToBuffer(ProductDto p) {
        productBuffer.put(p);
    }

}
