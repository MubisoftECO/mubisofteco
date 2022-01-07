package org.eco.mubisoft.good_and_cheap.user.domain.repo;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.good_and_cheap.user.domain.model.City;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p><b>City Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the city table.</p>
 */
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> getCitiesByProvince (Province province);
    City getCityByName(String name);
}
