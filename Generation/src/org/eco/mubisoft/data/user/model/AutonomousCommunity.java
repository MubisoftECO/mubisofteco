package org.eco.mubisoft.data.user.model;

/**
 * <p><b>Autonomous Community</b></p>
 * <p>Each location will have an autonomous community.</p>
 */
public class AutonomousCommunity {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the AC.</p>
     */
    private Long id;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the autonomous community.</p>
     */
    private String name;

    /**
     * <p><b>COUNTRY</b></p>
     * <p>Name of the country.</p>
     */
    private String country = "ES";

    public AutonomousCommunity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
