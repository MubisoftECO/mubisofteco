package org.eco.mubisoft.good_and_cheap.user.domain.model;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

/**
 * <p><b>Autonomous Community</b></p>
 * <p>Each location will have an autonomous community.</p>
 */
@Getter
@Entity
public class AutonomousCommunity {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the AC.</p>
     */
    @Id
    private Long id;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the autonomous community.</p>
     */
    @NotNull
    @Column(unique = true)
    private String name;

    /**
     * <p><b>COUNTRY</b></p>
     * <p>Name of the country.</p>
     */
    @NotNull
    private String country = "ES";
}
