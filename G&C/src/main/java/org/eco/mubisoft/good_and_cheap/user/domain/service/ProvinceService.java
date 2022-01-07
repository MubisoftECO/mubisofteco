package org.eco.mubisoft.good_and_cheap.user.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;

import java.util.List;

public interface ProvinceService {
    /**
     * <p><b>GET PROVINCE BY AUTONOMOUS COMMUNITY</b></p>
     * <p>Fetch a list of provinces given the autonomous community</p>
     * @param autonomousCommunity The province to take into account.
     * @return The list of provinces from the database.
     */
    List<Province> getProvincesByAutonomousCommunity(AutonomousCommunity autonomousCommunity);

    /**
     * <p><b>GET ALL PROVINCES</b></p>
     * <p>Get all the provinces from the database.</p>
     * @return The list of provinces.
     */
    List<Province> getAllProvinces();

    /**
     * <p><b>GET PROVINCE BY ID</b></p>
     * <p>Get a province form the database using its ID.</p>
     * @param id The id of the province that is going to be searched.
     * @return The province from the database.
     */
    Province getProvince(Long id);

    /**
     * <p><b>GET PROVINCE BY NAME</b></p>
     * <p>Get a province form the database using its name.</p>
     * @param name The name of the province that is going to be searched.
     * @return The province from the database.
     */
    Province getProvince(String name);
}
