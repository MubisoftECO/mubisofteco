package org.eco.mubisoft.good_and_cheap.product.domain.repo;

import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.eco.mubisoft.good_and_cheap.product.domain.service.ProductServiceFacade;
import org.eco.mubisoft.good_and_cheap.recipe.domain.model.Recipe;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findProductByVendor(AppUser vendor, Pageable pageable);
    Double countAllByVendor(AppUser vendor);
}
