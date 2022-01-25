package org.eco.mubisoft.good_and_cheap.product.domain.service;

import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts(Integer pageNum);
    List<String> getMeasurementUnits();
    Product getProduct(Long id);
    Product addProduct(Product product);
    boolean removeProduct(Long id);
    double countPages();
    List<ProductType> getIngredients();
    List<Product> getProductByVendor(AppUser vendor, int pageNum);
    double countPages(AppUser vendor);

    /**
     * <p><b>THREAD</b></p>
     * <p>Get | Set product from DB to buffer and from buffer to list.
     * List created for three different reasons (SOLD, EXPIRED, OTHER) </p>
     */
    List<ProductDto> getProductDtoListFromBuffer();
    List<ProductDto> getProductsInformationFromDB(Long id, String reason);
    void setProductsInformationToBuffer(ProductDto p);
    /**
     * <p><b>THREAD</b></p>
     * <p>Get | Set product from DB to buffer and from buffer to list.
     * List created for (SOLD) reason </p>
     */
    List<ProductSoldOnlyDto> getProductsSoldOnlyInformationFromDB(String city);
    void setProductsSoldOnlyInformationToBuffer(ProductSoldOnlyDto productSoldOnlyDto);
    List<ProductSoldOnlyDto> getProductsSoldOnlyListFromBuffer();

    void setProductsSoldOnlyTotalInformationToBuffer(MostLessSoldDetail mostLessSoldDetail);
    List<MostLessSoldDetail> getProductsSoldOnlyTotalListFromBuffer();

    void setProductsMostSoldInformationToBuffer(MostLeastSold mostLeastSold);

    List<MostLeastSold> getProductsMostSoldListFromBuffer();
}
