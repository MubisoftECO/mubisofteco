package org.eco.mubisoft.good_and_cheap.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * <p><b>Application role for users</b></p>
 * <p>User roles are use to give permissions to the different users of the application.
 * Depending on the roles, the user will have access to more or less functionalities
 * of the application.</p>
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the role.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * <p><b>Role Name</b></p>
     * <p>There are different types of roles on the application and depending on that role
     * the user will have permission to enter different sections of the application. The
     * roles are the following:</p>
     * <ul>
     *     <li><b>ROLE_USER</b>: Basic user role, it gives the user the ability of creating recipes.</li>
         *     <li><b>ROLE_VENDOR</b>: Basic role for vendors, it gives the user the ability of publishing
     *     products they want to sell and the ability of managing those products.</li>
     *     <li><b>ROLE_MANAGER</b>: This role allows the user to modify and remove any recipe that has
     *     been reported. It also has the ability to un-report recipes.</li>
     *     <li><b>ROLE_ADMIN</b>: This role allows the user to delete products, recipes and users from
     *     the application.</li>
     *     <li><b>ROLE_SUPER_ADMIN</b>: This role has permission to access any element of the application.</li>
     * </ul>
     */
    @Column(unique = true)
    private String name;
}
