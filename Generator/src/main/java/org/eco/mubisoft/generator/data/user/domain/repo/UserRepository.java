package org.eco.mubisoft.generator.data.user.domain.repo;

import org.eco.mubisoft.generator.data.user.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p><b>User Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the user table.</p>
 */
public interface UserRepository extends JpaRepository<AppUser, Long> {
}
