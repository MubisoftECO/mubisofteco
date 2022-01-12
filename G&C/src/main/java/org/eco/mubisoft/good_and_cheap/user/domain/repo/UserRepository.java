package org.eco.mubisoft.good_and_cheap.user.domain.repo;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Location;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * <p><b>User Repository</b></p>
 * <p>Interface that manages the queries executed by the program to get information
 * from the database about the user table.</p>
 */
public interface UserRepository extends JpaRepository<AppUser, Location> {

    Optional<AppUser> findById(Long id);

    /**
     * <p><b>FIND BY EMAIL</b></p>
     * <p>Search a user in the database using it's email. All emails in the database are
     * unique, so it will only get a maximum of one user.</p>
     * @param email The email of the user that is going to be searched.
     * @return Optional value, if the user email was found will return the user.
     */
    Optional<AppUser> findByUsername(String email);

    /**
     * <p><b>FIND BY EMAIL AND PASSWORD</b></p>
     * <p>Search a user in the database using it's email and password. All emails in the database
     * are unique, so it will only get a maximum of one user.</p>
     * @param email The email of the user that is going to be searched.
     * @param password The password of the user account referenced by the email.
     * @return Optional value, if the user email was found will return the user.
     */
    Optional<AppUser> findByUsernameAndPassword(String email, String password);

    /**
     * <p><b>FIND BY ROLE</b></p>
     * <p>Search users with a specific role in the database.</p>
     * @param role The role to filter with
     * @return Optional value, the list of users with a specific role.
     */
    List<AppUser> findAppUsersByRoles(Role role);
}
