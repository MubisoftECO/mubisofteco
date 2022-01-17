package org.eco.mubisoft.generator.data.user.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.generator.data.user.domain.model.*;
import org.eco.mubisoft.generator.data.user.domain.repo.*;
import org.eco.mubisoft.generator.connection.FileReader;
import org.eco.mubisoft.generator.control.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceFacade implements UserService {

    private final List<String> roleList = Arrays.asList(
            "ROLE_USER", "ROLE_VENDOR", "ROLE_MANAGER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN"
    );

    private final String[][] userNames = {
            {"Txema", "Perez"},
            {"Osane", "Lizarralde"},
            {"Oier", "Banos"},
            {"Joseba", "Izaguirre"},
            {"Oihane", "Lameirinhas"},
            {"Estalyn", "Curay"},
            {"Jon", "Navaridas"},
            {"Cliente", "Ejemplo"},
            {"Vendedor", "Ejemplo"},
            {"Manager", "Ejemplo"},
            {"Admin", "Ejemplo"},
            {"SuperAdmin", "Ejemplo"}
    };

    private final FileReader fileReader = new FileReader();
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;
    private final AutonomousCommunityRepository autonomousCommunityRepository;
    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Override
    public void generateRoles() {
        roleList.forEach(role -> {
            roleRepository.save(new Role(role));
        });
    }

    @Override
    public void generateAppUsers() {
        List<Role> roleList = roleRepository.findAll();
        List<City> cityList = cityRepository.findAll();
        Random random = new Random();

        for (long id = 1; id <= userNames.length - 5; id++) {
            Collection<Role> userRoles = new ArrayList<>();
            for (int i = 0; i < roleList.size(); i++) {
                Role role = roleList.get(random.nextInt(roleList.size()));
                if (!userRoles.contains(role)) {
                    userRoles.add(role);
                }
            }
            saveUser(userNames[(int) (id - 1)][0], userNames[(int) (id - 1)][1], cityList, userRoles);
        }

        // Create Basic Users
        saveUser(userNames[9][0], userNames[9][1], cityList, Collections.singletonList(roleList.get(0)));
        saveUser(userNames[10][0], userNames[10][1], cityList, Collections.singletonList(roleList.get(1)));
        saveUser(userNames[11][0], userNames[11][1], cityList, Collections.singletonList(roleList.get(2)));
        saveUser(userNames[7][0], userNames[7][1], cityList, Collections.singletonList(roleList.get(3)));
        saveUser(userNames[8][0], userNames[8][1], cityList, Collections.singletonList(roleList.get(4)));
    }

    private void saveUser(String name, String secondName, List<City> cityList, Collection<Role> roleList) {
        Random random = new Random();
        userRepository.save(new AppUser(
                name,
                secondName,
                name.toLowerCase() + "." + secondName.toLowerCase() + "@gmail.com",
                passwordEncoder.encode(name.toLowerCase() + "@" + secondName.toLowerCase()),
                roleList,
                locationRepository.save(new Location(
                    "Calle " + (random.nextInt(50) + 1),
                    cityList.get(random.nextInt(cityList.size()))
        ))));
    }

    @Override
    public void generateLocations() {
        Map<Long, AutonomousCommunity> autonomousCommunityMap = fileReader.getAutonomousCommunities(autonomousCommunityRepository);
        Map<Long, Province> provinceMap = fileReader.getProvinces(autonomousCommunityMap, provinceRepository);
        fileReader.getCities(provinceMap, cityRepository);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public List<AppUser> getAppUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAll() {
        locationRepository.deleteAll();
        cityRepository.deleteAll();
        provinceRepository.deleteAll();
        autonomousCommunityRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

}
