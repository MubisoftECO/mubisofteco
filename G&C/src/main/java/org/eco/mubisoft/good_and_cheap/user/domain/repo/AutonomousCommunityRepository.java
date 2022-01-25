package org.eco.mubisoft.good_and_cheap.user.domain.repo;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p><b>Autonomous Community Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the autonomous community table.</p>
 */
public interface AutonomousCommunityRepository extends JpaRepository<AutonomousCommunity, Long> {
    AutonomousCommunity getAutonomousCommunityByName (String name);
}
