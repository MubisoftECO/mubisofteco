package org.eco.mubisoft.good_and_cheap.user.domain.repo;

import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * <p><b>Role Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the role table.</p>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * <p><b>FIND BY NAME</b></p>
     * <p>Search a role in the database using it's name. All roles in the database
     * are unique, so it will only get a maximum of one role.</p>
     * @param name Name of the role that is going to be looked for.
     * @return Optional value, if the user email was found will return the role.
     */
    Optional<Role> findByName(String name);

}
