package org.eco.mubisoft.data.user.model;

/**
 * <p><b>Location of a user or business</b></p>
 * <p>Each of the users of the application will have a location. This location
 * will be used to find products, businesses or clients that are around them.</p>
 */
public class Location {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the location.</p>
     */
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
    private String city;
    /**
     * <p><b>PROVINCE</b></p>
     * <p>Province of the location.</p>
     */
    private String province;
    /**
     * <p><b>COUNTRY</b></p>
     * <p>Country of the location.</p>
     */
    private String country = "ES";

}
