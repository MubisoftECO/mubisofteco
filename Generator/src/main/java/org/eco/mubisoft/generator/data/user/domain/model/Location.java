package org.eco.mubisoft.generator.data.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
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

    public Location(String street, City city) {
        this.street = street;
        this.city = city;
    }
}
