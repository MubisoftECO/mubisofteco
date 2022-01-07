package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.RoleRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceFacade implements RoleService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} on the database.", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public Role getRole(String roleName) {
        log.info("Fetching role {} from database", roleName);
        return roleRepo.findByName(roleName).orElse(null);
    }

    @Override
    public Role getRole(Long id) {
        log.info("Fetching role {} from database", id);
        return roleRepo.findById(id).orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public Role deleteRole(Role role) {
        return null;
    }

    @Override
    public boolean setUserRole(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);

        AppUser user = userRepo.findByUsername(username).orElse(null);
        Role role = roleRepo.findByName(roleName).orElse(null);

        if (user == null || role == null) {
            log.error("The user or the role were null. Unable to add the role to the user.");
            return false;
        }
        else {
            user.getRoles().add(role);
        }
        return true;
    }

    @Override
    public boolean removeUserRole(AppUser user, Role role) {
        return false;
    }

}
