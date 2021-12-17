package org.eco.mubisoft.good_and_cheap.user.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;

/**
 * <p><b>USER SERVICE</b></p>
 * <p>Interface that manages the interactions between the controller
 * and the database.</p>
 */
public interface UserService {

    /**
     * <p><b>SAVE USER</b></p>
     * <p>Save a user on the database. It makes sure if the user's email is
     * unique on the database. If the email is already taken or any other error
     * is detected, the user won't be created.</p>
     * @param user The user that is going to be added.
     * @return The user that has been added on the database, with an encrypted password.
     */
    AppUser saveUser(AppUser user);

    /**
     * <p><b>SAVE ROLE</b></p>
     * <p>Save a new role on the database. Each role must be unique, so it will make
     * sure the role is not already created.</p>
     * @param role The role that is going to be added.
     * @return The user role that has been created on the database.
     */
    Role saveRole(Role role);

    /**
     * <p><b>DELETE USER</b></p>
     * <p>Remove a user from the database. If the user doesn't exist won't it return anything.</p>
     * @param user The user that is going to be removed.
     * @return The user that has been removed from the database.
     */
    AppUser deleteUser(AppUser user);

    /**
     * <p><b>DELETE ROLE</b></p>
     * <p>Delete a new role from the database. If the role doesn't exist it won't
     * return anything.</p>
     * @param role The role that is going to be removed.
     * @return The user role that has been removed from the database.
     */
    Role deleteRole(Role role);

    /**
     * <p><b>SET USER ROLE</b></p>
     * <p>Give a user a new role. As the user cannot have the same role multiple times, if
     * the user already has the role, it won't add it.</p>
     * @param user The user that is going to have a new role assigned.
     * @param role The role that is going to be assigned.
     * @return TRUE if the operation was successful, else FALSE.
     */
    boolean setUserRole(AppUser user, Role role);

    /**
     * <p><b>REMOVE USER ROLE</b></p>
     * <p>Remove a role from a user. If a user with a lower power role tries to remove
     * a higher power role, an error will happen.</p>
     * @param user The user that is going to have a role removed.
     * @param role The role that is going to be removed.
     * @return TRUE if the operation was successful, else FALSE.
     */
    boolean removeUserRole(AppUser user, Role role);
}
