package org.eco.mubisoft.generator.data.user.domain.repo;

import org.eco.mubisoft.generator.data.user.domain.model.City;
import org.eco.mubisoft.generator.data.user.domain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p><b>City Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the city table.</p>
 */
public interface CityRepository extends JpaRepository<City, Long> {
}
