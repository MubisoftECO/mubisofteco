package org.eco.mubisoft.good_and_cheap.product.domain.repo;

import org.eco.mubisoft.good_and_cheap.product.domain.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {

    @Query(value = "select distinct(pt.measurementUnit) from ProductType pt")
    List<String> getAllMeasurementUnits();

    @Query("select p from ProductType p where p.nameEn like concat(?1, '%')")
    List<ProductType> getAllByName_enStartingWith(String filter);

    @Query("select p from ProductType p where p.nameEs like concat(?1, '%')")
    List<ProductType> getAllByName_esStartingWith(String filter);

    @Query("select p from ProductType p where p.nameEu like concat(?1, '%')")
    List<ProductType> getAllByName_euStartingWith(String filter);

}
