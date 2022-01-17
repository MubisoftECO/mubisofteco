package org.eco.mubisoft.generator.data.user.domain.repo;

import org.eco.mubisoft.generator.data.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.generator.data.user.domain.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * <p><b>Province Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the province table.</p>
 */
public interface ProvinceRepository extends JpaRepository<Province, Long> {
}
