package org.eco.mubisoft.generator.data.user.domain.repo;

import org.eco.mubisoft.generator.data.user.domain.model.AutonomousCommunity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p><b>Autonomous Community Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the autonomous community table.</p>
 */
public interface AutonomousCommunityRepository extends JpaRepository<AutonomousCommunity, Long> {
}
