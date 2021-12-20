package org.eco.mubisoft.good_and_cheap.user.domain.model;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @Column(unique = true)
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
    @ManyToMany(fetch = FetchType.EAGER)
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
    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;
}