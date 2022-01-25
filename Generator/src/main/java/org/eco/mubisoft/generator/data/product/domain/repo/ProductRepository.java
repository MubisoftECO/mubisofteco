package org.eco.mubisoft.generator.data.product.domain.repo;

import org.eco.mubisoft.generator.data.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
