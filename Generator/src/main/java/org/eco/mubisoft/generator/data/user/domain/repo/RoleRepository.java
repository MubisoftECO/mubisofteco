package org.eco.mubisoft.generator.data.user.domain.repo;

import org.eco.mubisoft.generator.data.user.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p><b>Role Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the role table.</p>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
