package org.eco.mubisoft.data.user.dao;

import org.eco.mubisoft.data.user.model.AppUser;
import org.eco.mubisoft.data.user.model.City;
import org.eco.mubisoft.data.user.model.Location;
import org.eco.mubisoft.data.user.model.Role;

import java.util.List;

public interface UserDAO {

    void insertRoles(List<Role> userRoles);
    List<Role> getRoles();
    void deleteRoles();
    void insertCities(List<City> cities);
    void insertLocation(Location location);
    void deleteLocations();
    void insertUser(AppUser user);
    void deleteUsers();

}
