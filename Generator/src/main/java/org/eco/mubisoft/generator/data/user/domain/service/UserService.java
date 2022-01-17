package org.eco.mubisoft.generator.data.user.domain.service;

import org.eco.mubisoft.generator.data.user.domain.model.AppUser;
import org.eco.mubisoft.generator.data.user.domain.model.Role;

import java.util.List;

public interface UserService {

    void generateRoles();
    void generateAppUsers();
    void generateLocations();
    List<Role> getRoles();
    List<AppUser> getAppUsers();
    void deleteAll();

}
