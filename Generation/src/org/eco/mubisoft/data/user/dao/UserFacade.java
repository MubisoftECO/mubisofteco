package org.eco.mubisoft.data.user.dao;

import javafx.collections.transformation.FilteredList;
import org.eco.mubisoft.data.user.model.AppUser;
import org.eco.mubisoft.data.user.model.Location;
import org.eco.mubisoft.data.user.model.Role;
import org.eco.mubisoft.generator.connection.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserFacade {

    private final UserDAO userDAO = new UserMysqlDAO();

    private final List<String> roleList = Arrays.asList(
        "ROLE_USER", "ROLE_VENDOR", "ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"
    );

    public void generateRoles() {
        List<Role> roles = new ArrayList<>();

        for (long id = 1L; id <= roleList.size(); id++) {
            roles.add(new Role(id, roleList.get((int) (id - 1))));
        }
        userDAO.insertRoles(roles);
    }

    public List<Role> getRoles() {
        return userDAO.getRoles();
    }

    public void generateLocations() {
        userDAO.insertCities(new FileReader().getCities());
    }

    public void generateAppUsers() {
        userDAO.insertUser(null);
        userDAO.insertLocation(null);
    }

    public List<AppUser> getAppUsers() {
        return Arrays.asList(null, null, null);
    }

    public void deleteAll() {
        userDAO.deleteUsers();
        userDAO.deleteRoles();
        userDAO.deleteLocations();
    }
}
