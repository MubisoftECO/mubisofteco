package org.eco.mubisoft.good_and_cheap.user.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.persistence.*;

/**
 * <p><b>Autonomous Community</b></p>
 * <p>Each location will have an autonomous community.</p>
 */
@Getter
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class AutonomousCommunity {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the AC.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the autonomous community.</p>
     */

    @Column(unique = true, nullable = false)
    private String name;

    /**
     * <p><b>COUNTRY</b></p>
     * <p>Name of the country.</p>
     */
    @Column(nullable = false)
    private String country = "ES";
}
