package org.eco.mubisoft.good_and_cheap.user.domain.model;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
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
    @ManyToOne(fetch = FetchType.LAZY)
    private AutonomousCommunity autonomousCommunity;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the Province.</p>
     */
    private String name;




}
