package org.eco.mubisoft.good_and_cheap.user.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.City;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;

import java.util.List;

public interface CityService {
    /**
     * <p><b>GET CITIES BY PROVINCE</b></p>
     * <p>Fetch a list of cities given the province</p>
     * @param province The province to take into account.
     * @return The list of cities by province.
     */
    List<City> getCitiesByProvince(Province province);

    /**
     * <p><b>GET CITY BY ID</b></p>
     * <p>Get a city form the database using its ID.</p>
     * @param id The id of the city that is going to be searched.
     * @return The city from the database.
     */
    City getCity(Long id);

    /**
     * <p><b>GET CITY BY NAME</b></p>
     * <p>Get a city form the database using its name.</p>
     * @param name The name of the city that is going to be searched.
     * @return The city from the database.
     */
    City getCity(String name);
}
