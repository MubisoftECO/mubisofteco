package org.eco.mubisoft.good_and_cheap.user.domain.service;

import org.eco.mubisoft.good_and_cheap.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Province;

import java.util.List;

public interface AutonomousCommunityService {

    /**
     * <p><b>GET ALL AUTONOMOUS COMMUNITIES</b></p>
     * <p>Get all the autonomous communities from the database.</p>
     * @return The list of autonomous communities.
     */
    List<AutonomousCommunity> getAllAutonomousCommunities();

    /**
     * <p><b>GET AUTONOMOUS COMMUNITY BY ID</b></p>
     * <p>Get a autonomous community form the database using its ID.</p>
     * @param id The id of the autonomous community that is going to be searched.
     * @return The autonomous community from the database.
     */
    AutonomousCommunity getAutonomousCommunity(Long id);

    /**
     * <p><b>GET AUTONOMOUS COMMUNITY BY NAME</b></p>
     * <p>Get a autonomous community form the database using its name.</p>
     * @param name The name of the autonomous community that is going to be searched.
     * @return The autonomous community from the database.
     */
    AutonomousCommunity getAutonomousCommunity(String name);
}
