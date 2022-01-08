package org.eco.mubisoft.data.user.dao;

import org.eco.mubisoft.data.user.model.AppUser;
import org.eco.mubisoft.data.user.model.City;
import org.eco.mubisoft.data.user.model.Location;
import org.eco.mubisoft.data.user.model.Role;

import java.util.Collection;
import java.util.List;

public interface UserDAO {

    void insertRoles(List<Role> userRoles);
    void setUserRole(Long userId, Collection<Long> roleIdList);
    List<Role> getRoles();
    void deleteRoles();
    void insertCities(List<City> cities);
    List<City> getCities();
    void insertLocation(Location location);
    void deleteLocations();
    void insertUser(AppUser user);
    List<Long> getAppUserIds();
    void deleteUsers();

}
