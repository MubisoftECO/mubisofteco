package org.eco.mubisoft.good_and_cheap.user.domain.model;

import lombok.*;

import javax.persistence.*;

/**
 * <p><b>Location of a user or business</b></p>
 * <p>Each of the users of the application will have a location. This location
 * will be used to find products, businesses or clients that are around them.</p>
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    /**
     * <p><b>ID</b></p>
     * <p>ID of the location.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * <p><b>STREET</b></p>
     * <p>Street of the location. This value is optional for users with the ROLE_USER.</p>
     */
    private String street;
    /**
     * <p><b>CITY</b></p>
     * <p>City of the location.</p>
     */
    @ManyToOne
    private City city;
    /**
     * <p><b>PROVINCE</b></p>
     * <p>Province of the location.</p>
     */
    @ManyToOne
    private Province province;
    /**
     * <p><b>AUTONOMOUS COMMUNITY</b></p>
     * <p>Autonomous Community of the location.</p>
     */
    @ManyToOne
    private AutonomousCommunity autonomousCommunity;
    /**
     * <p><b>COUNTRY</b></p>
     * <p>Country of the location.</p>
     */
    private String country = "ES";

}
