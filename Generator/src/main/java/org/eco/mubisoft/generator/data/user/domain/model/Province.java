package org.eco.mubisoft.generator.data.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Province {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the Provice. The id is created taking the postal code into account.
     * The first two digits of the PC are used.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;

    /**
     * <p><b>AUTONOMOUS COMMUNITY</b></p>
     * <p>ID of the AC.</p>
     */
    @ManyToOne
    private AutonomousCommunity autonomousCommunity;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the Province.</p>
     */
    private String name;

    public Province(AutonomousCommunity autonomousCommunity, String name) {
        this.autonomousCommunity = autonomousCommunity;
        this.name = name;
    }
}
