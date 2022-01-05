package org.eco.mubisoft.good_and_cheap.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Location;
import org.eco.mubisoft.good_and_cheap.user.domain.model.Role;
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
        //Aqui marca un code smell. Segun sonar, la funcion findAll no devuelve null. Pero segun internet, si.
        //Asi que compruebo si es null para devolver una lista vacia en vez de un null. Para que luego el
        //programa no se queje.
        List<AppUser> userList = userRepo.findAll();
        if (userList == null){
            userList = new ArrayList<>();
        }
        return userList;
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
        if(location != null && !Objects.equals(user.getLocation().getCity().getName(), location.getCity().getName())){
           Location locationToEdit = locationRepo.getById(user.getLocation().getId());
           locationToEdit.setAutonomousCommunity(location.getAutonomousCommunity());
           locationToEdit.setProvince(location.getProvince());
           locationToEdit.setCity(location.getCity());
        }
        if(imageSrc != null && !Objects.equals(user.getImgSrc(), imageSrc)){
            user.setImgSrc(imageSrc);
        }

        return user;
    }

    @Override
    public boolean checkPassword(String username, String password) {
        AppUser user = userRepo.findByUsername(username).orElse(null);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    @Transactional
    public void updatePassword(String username, String password) {
        AppUser user = userRepo.findByUsername(username).orElse(null);
        user.setPassword(passwordEncoder.encode(password));
    }

    @Override
    public List<AppUser> getUsersByRole(Role role) {
        log.info("Fetching users with role {}", role.getName());
        return userRepo.findAppUsersByRoles(role);
    }
}
