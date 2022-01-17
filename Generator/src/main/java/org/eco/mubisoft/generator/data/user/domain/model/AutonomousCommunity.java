package org.eco.mubisoft.generator.data.user.domain.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <p><b>Autonomous Community</b></p>
 * <p>Each location will have an autonomous community.</p>
 */
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
    @NotNull
    @Column(unique = true)
    private String name;

    /**
     * <p><b>COUNTRY</b></p>
     * <p>Name of the country.</p>
     */
    @NotNull
    private String country = "ES";

    public AutonomousCommunity(String name) {
        this.name = name;
    }

}
