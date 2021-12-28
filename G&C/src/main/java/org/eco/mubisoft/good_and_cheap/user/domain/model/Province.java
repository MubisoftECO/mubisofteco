package org.eco.mubisoft.good_and_cheap.user.domain.model;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Entity
public class Province {
    /**
     * <p><b>Provice</b></p>
     * <p>Each location will have a province. The province will be linked with the autonomous community.</p>
     */

    /**
     * <p><b>ID</b></p>
     * <p>ID of the Provice. The id is created taking the postal code into account.
     * The first two digits of the PC are used.</p>
     */
    @Id
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




}
