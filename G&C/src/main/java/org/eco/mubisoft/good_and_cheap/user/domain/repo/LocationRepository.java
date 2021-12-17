package org.eco.mubisoft.good_and_cheap.user.domain.repo;

import org.eco.mubisoft.good_and_cheap.user.domain.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p><b>Location Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the location table.</p>
 */
public interface LocationRepository extends JpaRepository<Location, Long> {
}
