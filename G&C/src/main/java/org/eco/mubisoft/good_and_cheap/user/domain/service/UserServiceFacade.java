package org.eco.mubisoft.good_and_cheap.user.domain.service;

import antlr.actions.python.CodeLexer;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.application.security.TokenService;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Location;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.LocationRepository;
import org.eco.mubisoft.good_and_cheap.user.domain.repo.UserRepository;
import org.eco.mubisoft.good_and_cheap.user.thread.UserBuffer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceFacade implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final LocationRepository locationRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserBuffer userBuffer;

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
        log.info("Deleting user {} from database", user.getUsername());
        userRepo.delete(user);
        return user;
    }

    @Override
    public List<AppUser> getAllUsers(){
        log.info("Fetching all the users from the database");
        return userRepo.findAll();
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

    @Override
    @Transactional
    public AppUser updateUser(Long id, String name, String secondName, String username, Location location, String imageSrc) {
        AppUser user = userRepo.findById(id).orElseThrow(() -> new IllegalStateException(
                "User not found when updating user"
        ));
        if (name != null && name.length() > 0 && !Objects.equals(user.getName(), name)){
            user.setName(name);
        }
        if (secondName != null && secondName.length() > 0 && !Objects.equals(user.getSecondName(), secondName)){
            user.setSecondName(secondName);
        }
        if (username != null && username.length() > 0 && !Objects.equals(user.getUsername(), username)){
            user.setUsername(username);
        }
        if (location != null && !Objects.equals(user.getLocation().getCity().getName(), location.getCity().getName())){
           Location locationToEdit = locationRepo.getById(user.getLocation().getId());
           locationToEdit.setCity(location.getCity());
        }
        if (location != null && !Objects.equals(user.getLocation().getStreet(), location.getStreet())){
            Location locationToEdit = locationRepo.getById(user.getLocation().getId());
            locationToEdit.setStreet(location.getStreet());
        }
        if (imageSrc != null && !Objects.equals(user.getImgSrc(), imageSrc)){
            user.setImgSrc(imageSrc);
        }

        return user;
    }

    @Override
    public boolean checkPassword(String username, String password) {
        AppUser user = userRepo.findByUsername(username).orElse(null);
        return passwordEncoder.matches(password, Objects.requireNonNull(user).getPassword());
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) {
        userRepo.findByUsername(username).ifPresent(user -> user.setPassword(passwordEncoder.encode(password)));
    }

    @Override
    public List<AppUser> getUsersByRole(Role role) {
        log.info("Fetching users with role {}", role.getName());
        return userRepo.findAppUsersByRoles(role);
    }

    @Override
    public AppUser getLoggedUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        TokenService tokenService = new TokenService();
        String username;

        try {
            username = tokenService.getUsernameFromToken((String) session.getAttribute("accessToken"));
            if (username == null) {
                throw new NullPointerException("Access token user is null");
            }
        } catch (JWTVerificationException | NullPointerException e) {
            username = tokenService.getUsernameFromToken((String) session.getAttribute("refreshToken"));
        }
        return this.getUser(username);
    }

    @Override
    public Collection<String> getRolesFromLoggedUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        TokenService tokenService = new TokenService();

        Collection<String> roles;
        try {
            roles = tokenService.getRolesFromToken((String) session.getAttribute("accessToken"));
        } catch (JWTVerificationException e) {
            roles = tokenService.getRolesFromToken((String) session.getAttribute("refreshToken"));
        }
        return roles;
    }

    @Override
    public boolean userHasRole(AppUser user, String role) {
        try {
            Collection<Role> roles = user.getRoles();
            return roles.stream().anyMatch(r -> r.getName().equals(role));
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public List<Long> getIdListFromDB(String city) {
        return userRepo.getIdListByCity(city);
    }

    @Override
    public void setIdListToBuffer(Long id) {
        try {
            userBuffer.put(id);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Long> getIdListFromBuffer() {
        List<Long> list = new ArrayList<>();

        while(userBuffer.getBufferSize() > 0) {
            try {
                list.add(userBuffer.get());
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
        return list;
    }
}
