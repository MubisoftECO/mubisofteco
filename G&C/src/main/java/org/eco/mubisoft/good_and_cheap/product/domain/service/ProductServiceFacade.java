package org.eco.mubisoft.good_and_cheap.product.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.thread.MostLeastSoldBuffer;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.domain.repo.ProductRepository;
import org.eco.mubisoft.good_and_cheap.product.domain.repo.ProductTypeRepository;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductBuffer;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductSoldOnlyBuffer;
import org.eco.mubisoft.good_and_cheap.product.thread.ProductSoldOnlyTotalBuffer;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
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
    private final ProductSoldOnlyBuffer productSoldOnlyBuffer;
    private final ProductSoldOnlyTotalBuffer productSoldOnlyTotalBuffer;
    private final MostLeastSoldBuffer mostLeastSoldBuffer;

    @Override
    public List<Product> getProductByVendor(AppUser vendor, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, (int) ELEMENT_NUM);
        return productRepository.findProductByVendor(vendor, pageable).toList();
    }

    @Override
    public double countPages(AppUser vendor) {
        return Math.ceil(productRepository.countAllByVendor(vendor) / ELEMENT_NUM);
    }

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
    public Product addProduct(Product product) {
        return productRepository.save(product);
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
            try {
                list.add(productBuffer.get());
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return list;
    }

    @Override
    public List<ProductDto> getProductsInformationFromDB(Long id, String reason) {
        Query query = entityManager.createNativeQuery("SELECT pt.name_en as 'nameEn', pt.name_es as 'nameEs', pt.name_eu as 'nameEu', SUM(p.price) as 'total', p.remove_reason as 'reason' FROM product p JOIN product_type pt on pt.id = p.product_type_id WHERE (p.vendor_id = ?1) AND (p.remove_reason = ?2) GROUP BY pt.id","ProductDetailsMapping");
        query.setParameter(1, id);
        query.setParameter(2,reason);

        return query.getResultList();
    }

    @Override
    public void setProductsInformationToBuffer(ProductDto p) {
        try {
            productBuffer.put(p);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public List<ProductSoldOnlyDto> getProductsSoldOnlyInformationFromDB(String city) {
        Query query = entityManager.createNativeQuery("SELECT pt.name_en as 'nameEn', pt.name_es as 'nameEs', pt.name_eu as 'nameEu', p.price as 'price', p.quantity as 'quantity' FROM product p JOIN product_type pt on pt.id = p.product_type_id JOIN app_user au on p.vendor_id = au.id JOIN location l on au.location_id = l.id JOIN city c on l.city_id = c.id WHERE (p.remove_reason = 'SOLD') AND (c.name = ?1)","ProductMostLessMapping");
        query.setParameter(1,city);

        return query.getResultList();
    }

    @Override
    public void setProductsSoldOnlyInformationToBuffer(ProductSoldOnlyDto productSoldOnlyDto) {
        try {
            productSoldOnlyBuffer.put(productSoldOnlyDto);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public List<ProductSoldOnlyDto> getProductsSoldOnlyListFromBuffer() {
        List<ProductSoldOnlyDto> list = new ArrayList<>();

        while(productSoldOnlyBuffer.getBufferSize() > 0) {
            try {
                list.add(productSoldOnlyBuffer.get());
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return list;
    }

    @Override
    public void setProductsSoldOnlyTotalInformationToBuffer(MostLessSoldDetail mostLessSoldDetail) {
        try {
            productSoldOnlyTotalBuffer.put(mostLessSoldDetail);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public List<MostLessSoldDetail> getProductsSoldOnlyTotalListFromBuffer() {
        List<MostLessSoldDetail> list = new ArrayList<>();

        while(productSoldOnlyTotalBuffer.getBufferSize() > 0) {
            try {
                list.add(productSoldOnlyTotalBuffer.get());
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        return list;
    }

    @Override
    public void setProductsMostSoldInformationToBuffer(MostLeastSold mostLeastSold) {
        try {
            mostLeastSoldBuffer.put(mostLeastSold);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public List<MostLeastSold> getProductsMostSoldListFromBuffer() {
        List<MostLeastSold> list = new ArrayList<>();

        while(mostLeastSoldBuffer.getBufferSize() > 0) {
            try {
                list.add(mostLeastSoldBuffer.get());
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        return list;
    }

}
