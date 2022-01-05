package org.eco.mubisoft.data.user.model;

/**
 * <p><b>CITY</b></p>
 * <p>Each location will have a city. The city will be linked with the province.</p>
 */
public class City {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the City.</p>
     */
    private Long id;

    /**
     * <p><b>Province</b></p>
     * <p>ID of the Province of the city.</p>
     */
    private Province province;

    /**
     * <p><b>cityCode</b></p>
     * <p>NOT UNIQUE, code of the city in the autonomous community. Each city has a code.
     * Two cities in different AC might have the same code.</p>
     */
    private int cityCode;

    /**
     * <p><b>CD</b></p>
     * <p>Contol Digit (Dígito de control). According to INE (Insituto Nacional de Estadística)
     * it is used to detect errors. INE has not published how to calculate it.</p>
     */
    private int CD;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the City.</p>
     */
    private String name;

    public City(Long id, Province province, int cityCode, int CD, String name) {
        this.id = id;
        this.province = province;
        this.cityCode = cityCode;
        this.CD = CD;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getProvinceID() {
        return province.getId();
    }

    public Province getProvince() { return  province; }

    public int getCityCode() {
        return cityCode;
    }

    public int getCD() {
        return CD;
    }

    public String getName() {
        return name;
    }
}
