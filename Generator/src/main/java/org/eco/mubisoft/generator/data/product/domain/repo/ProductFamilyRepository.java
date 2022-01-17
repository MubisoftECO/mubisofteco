package org.eco.mubisoft.generator.data.product.domain.repo;

import org.eco.mubisoft.generator.data.product.domain.model.ProductFamily;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFamilyRepository extends JpaRepository<ProductFamily, Long> {
}
