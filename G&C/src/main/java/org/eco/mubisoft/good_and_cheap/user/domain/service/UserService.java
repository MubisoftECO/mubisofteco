package org.eco.mubisoft.good_and_cheap.user.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import javax.transaction.Transactional;
import java.util.List;

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
     * <p><b>GET USER BY ID</b></p>
     * <p>Get a user form the database using it's user ID.</p>
     * @param id The ID of the user that is going to be searched.
     * @return The user from the database.
     */
    AppUser getUser(Long id);

    /**
     * <p><b>GET USER BY USERNAME</b></p>
     * <p>Get a user form the database using its email.</p>
     * @param username The email of the user that is going to be searched.
     * @return The user from the database.
     */
    AppUser getUser(String username);

    /**
     * <p><b>DELETE USER</b></p>
     * <p>Remove a user from the database. If the user doesn't exist won't it return anything.</p>
     * @param user The user that is going to be removed.
     * @return The user that has been removed from the database.
     */
    AppUser deleteUser(AppUser user);

    /**
     * <p><b>GET ALL USERS</b></p>
     * <p>Fetch all the users from the database. If there are no users, it returns a empty list.</p>
     * @return The list of users from the database.
     */

    List<AppUser> getAllUsers();

    AppUser editUser (AppUser user);

    AppUser updateUser(Long id, String name, String secondName, String username);
}
