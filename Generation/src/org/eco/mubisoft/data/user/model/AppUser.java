package org.eco.mubisoft.data.user.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * <p><b>User of the application</b></p>
 * <p>The user class is used to manage the access to different application
 * functionalities and to store the information of the different users.</p>
 */
public class AppUser {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the role.</p>
     */
    private Long id;
    /**
     * <p><b>NAME</b></p>
     * <p>Depending on the user, the name attribute will store the first name of the user or
     * the CIF number of the business.</p>
     */
    private String name;
    /**
     * <p><b>SECOND NAME</b></p>
     * <p>Depending on the user, the name attribute will store the second name of the user or
     * the name of the business.</p>
     */
    private String secondName;
    /**
     * <p><b>USERNAME</b></p>
     * <p>An email will be used as the username for the account. It will be used by
     * the authentication system. Each email must be unique.</p>
     */
    private String username;
    /**
     * <p><b>PASSWORD</b></p>
     * <p>Authentication values for the user.</p>
     */
    private String password;
    /**
     * <p><b>ROLES</b></p>
     * <p>A list with the different roles of the user.</p>
     */
    private Collection<Role> roles = new ArrayList<>();

    /**
     * <p><b>IMAGE SOURCE</b></p>
     * <p>A list with the different roles of the user.</p>
     */
    private String imgSrc = null;

    /**
     * <p><b>LOCATION</b></p>
     * <p>The location of the user.</p>
     */
    private Location location;

    public AppUser(Long id) {
        this.id = id;
    }

    public AppUser(Long id, String name, String secondName, String username, String password,
                   Collection<Role> roles, Location location) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<Long> getRoles() {
        Collection<Long> roles = new ArrayList<>();

        this.roles.forEach(role -> {
            roles.add(role.getId());
        });
        return roles;
    }

    public Location getLocation() {
        return location;
    }

    public Long getLocationID() {
        return location.getId();
    }
}