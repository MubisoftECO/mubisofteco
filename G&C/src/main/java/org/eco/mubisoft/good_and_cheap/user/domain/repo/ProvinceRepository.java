package org.eco.mubisoft.good_and_cheap.user.domain.repo;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <p><b>Province Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the province table.</p>
 */
public interface ProvinceRepository extends JpaRepository<Province, Long> {
   List<Province> getProvincesByAutonomousCommunity (AutonomousCommunity autonomousCommunity);
   Province getProvinceByName (String name);
}
