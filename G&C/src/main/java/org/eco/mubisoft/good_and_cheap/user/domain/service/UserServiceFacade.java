package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.LocationRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.RoleRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceFacade implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final LocationRepository locationRepo;

    @Override
    public AppUser saveUser(AppUser user) {
        return null;
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public AppUser deleteUser(AppUser user) {
        return null;
    }

    @Override
    public Role deleteRole(Role role) {
        return null;
    }

    @Override
    public boolean setUserRole(AppUser user, Role role) {
        return false;
    }

    @Override
    public boolean removeUserRole(AppUser user, Role role) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
