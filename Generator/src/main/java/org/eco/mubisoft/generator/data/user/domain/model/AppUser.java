package org.eco.mubisoft.generator.data.user.domain.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p><b>User of the application</b></p>
 * <p>The user class is used to manage the access to different application
 * functionalities and to store the information of the different users.</p>
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the role.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Long id;
    /**
     * <p><b>NAME</b></p>
     * <p>Depending on the user, the name attribute will store the first name of the user or
     * the CIF number of the business.</p>
     */
    @NotNull
    private String name;
    /**
     * <p><b>SECOND NAME</b></p>
     * <p>Depending on the user, the name attribute will store the second name of the user or
     * the name of the business.</p>
     */
    @NotNull
    private String secondName;
    /**
     * <p><b>USERNAME</b></p>
     * <p>An email will be used as the username for the account. It will be used by
     * the authentication system. Each email must be unique.</p>
     */
    @NotNull
    @Column(unique = true)
    private String username;
    /**
     * <p><b>PASSWORD</b></p>
     * <p>Authentication values for the user.</p>
     */
    @NotNull
    private String password;
    /**
     * <p><b>ROLES</b></p>
     * <p>A list with the different roles of the user.</p>
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Role> roles = new ArrayList<>();

    /**
     * <p><b>IMAGE SOURCE</b></p>
     * <p>The name of the image file.</p>
     */

    private String imgSrc = null;

    /**
     * <p><b>LOCATION</b></p>
     * <p>The location of the user.</p>
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;

    public AppUser(String name, String secondName, String username, String password, Collection<Role> roles, Location location) {
        this.name = name;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.location = location;
    }
}