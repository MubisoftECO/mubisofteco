package org.eco.mubisoft.good_and_cheap.user.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;

import java.util.List;

public interface RoleService {

    /**
     * <p><b>SAVE ROLE</b></p>
     * <p>Save a new role on the database. Each role must be unique, so it will make
     * sure the role is not already created.</p>
     * @param role The role that is going to be added.
     * @return The user role that has been created on the database.
     */
    Role saveRole(Role role);

    /**
     * <p><b>GET ROLE BY NAME</b></p>
     * <p>Get a role form the database using its name.</p>
     * @param roleName The name of the role that is going to be searched.
     * @return The role from the database.
     */
    Role getRole(String roleName);
    /**
     * <p><b>GET ROLE BY id</b></p>
     * <p>Get a role form the database using id.</p>
     * @param id The id of the role that is going to be searched.
     * @return The role from the database.
     */
    Role getRole(Long id);

    /**
     * <p><b>GET ALL ROLES</b></p>
     * <p>Get all roles form the database.</p>
     * @return The roles from the database.
     */
    List<Role> getAllRoles();

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
     * @param username The user that is going to have a new role assigned.
     * @param role The role that is going to be assigned.
     * @return TRUE if the operation was successful, else FALSE.
     */
    boolean setUserRole(String username, String role);

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
