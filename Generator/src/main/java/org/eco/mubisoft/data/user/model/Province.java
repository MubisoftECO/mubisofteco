package org.eco.mubisoft.data.user.model;

/**
 * <p><b>Province</b></p>
 * <p>Each location will have a province. The province will be linked with the autonomous community.</p>
 */
public class Province {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the Province. The id is created taking the postal code into account.
     * The first two digits of the PC are used.</p>
     */
    private Long id;

    /**
     * <p><b>AUTONOMOUS COMMUNITY</b></p>
     * <p>ID of the AC.</p>
     */
    private AutonomousCommunity autonomousCommunity;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the Province.</p>
     */
    private String name;

    public Province(Long id, AutonomousCommunity autonomousCommunity, String name) {
        this.id = id;
        this.autonomousCommunity = autonomousCommunity;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getAutonomousCommunityID() {
        return autonomousCommunity.getId();
    }

    public AutonomousCommunity getAutonomousCommunity() {
        return autonomousCommunity;
    }

    public String getName() {
        return name;
    }
}
