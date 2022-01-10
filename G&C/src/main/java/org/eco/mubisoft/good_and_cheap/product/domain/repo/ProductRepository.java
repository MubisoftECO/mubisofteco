package org.eco.mubisoft.good_and_cheap.product.domain.repo;

import org.eco.mubisoft.good_and_cheap.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
