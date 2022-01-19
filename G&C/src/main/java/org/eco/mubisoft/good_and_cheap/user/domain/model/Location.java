package org.eco.mubisoft.good_and_cheap.user.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
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
    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

}
