package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.LocationRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceFacade implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final LocationRepository locationRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving user {} on the database", user.getUsername());
        return userRepo.save(user);
    }

    @Override
    public AppUser getUser(Long id) {
        log.info("Fetching user with id {} from database", id);
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {} from database", username);
        return userRepo.findByUsername(username).orElse(null);
    }

    @Override
    public AppUser deleteUser(AppUser user) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username).orElse(null);

        // Check if the database found the user.
        if (user == null) {
            log.error("User {} not found.", username);
            throw new UsernameNotFoundException("Couldn't find the user.");
        } else {
            log.info("User {} found, logging in with user.", username);
        }

        // Get the roles of the user
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        // Return the user, password and authorities.
        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
