package org.eco.mubisoft.data.user.dao;

import org.eco.mubisoft.data.user.model.AppUser;
import org.eco.mubisoft.data.user.model.City;
import org.eco.mubisoft.data.user.model.Location;
import org.eco.mubisoft.data.user.model.Role;
import org.eco.mubisoft.generator.connection.FileReader;
import org.eco.mubisoft.generator.control.PasswordEncoder;
import org.springframework.security.core.parameters.P;

import java.util.*;

public class UserFacade {

    private final UserDAO userDAO = new UserMysqlDAO();
    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

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
        List<Role> roleList = userDAO.getRoles();
        List<City> cityList = userDAO.getCities();
        Random random = new Random();

        for (long id = 1; id <= userNames.length - 5; id++) {
            Collection<Role> userRoles = new ArrayList<>();
            for (int i = 0; i < roleList.size(); i++) {
                Role role = roleList.get(random.nextInt(roleList.size()));
                if (!userRoles.contains(role)) {
                    userRoles.add(role);
                }
            }
            this.insertUser(new AppUser(
                    id, userNames[(int) (id - 1)][0], userNames[(int) (id - 1)][1],
                    userNames[(int) (id - 1)][0].toLowerCase() + "." + userNames[(int) (id - 1)][1].toLowerCase() + "@gmail.com",
                    passwordEncoder.encode(userNames[(int) (id - 1)][0].toLowerCase() + "@" + userNames[(int) (id - 1)][1].toLowerCase()),
                    userRoles, this.getRandomLocation(id, random, cityList)
            ));
        }

        // Create Basic Users
        this.insertUser(new AppUser(
            8L, userNames[7][0], userNames[7][1],
                userNames[7][0].toLowerCase() + "." + userNames[7][1].toLowerCase() + "@gmail.com",
                passwordEncoder.encode(userNames[7][0].toLowerCase() + "@" + userNames[7][1].toLowerCase()),
                Collections.singletonList(roleList.get(0)), this.getRandomLocation(8L, random, cityList)
        ));
        this.insertUser(new AppUser(
                9L, userNames[8][0], userNames[8][1],
                userNames[8][0].toLowerCase() + "." + userNames[8][1].toLowerCase() + "@gmail.com",
                passwordEncoder.encode(userNames[8][0].toLowerCase() + "@" + userNames[8][1].toLowerCase()),
                Collections.singletonList(roleList.get(1)), this.getRandomLocation(9L, random, cityList)
        ));
        this.insertUser(new AppUser(
                10L, userNames[9][0], userNames[9][1],
                userNames[9][0].toLowerCase() + "." + userNames[9][1].toLowerCase() + "@gmail.com",
                passwordEncoder.encode(userNames[9][0].toLowerCase() + "@" + userNames[9][1].toLowerCase()),
                Collections.singletonList(roleList.get(2)), this.getRandomLocation(10L, random, cityList)
        ));
        this.insertUser(new AppUser(
                11L, userNames[10][0], userNames[7][1],
                userNames[10][0].toLowerCase() + "." + userNames[10][1].toLowerCase() + "@gmail.com",
                passwordEncoder.encode(userNames[10][0].toLowerCase() + "@" + userNames[10][1].toLowerCase()),
                Collections.singletonList(roleList.get(3)), this.getRandomLocation(11L, random, cityList)
        ));
        this.insertUser(new AppUser(
                12L, userNames[11][0], userNames[7][1],
                userNames[11][0].toLowerCase() + "." + userNames[11][1].toLowerCase() + "@gmail.com",
                passwordEncoder.encode(userNames[11][0].toLowerCase() + "@" + userNames[11][1].toLowerCase()),
                Collections.singletonList(roleList.get(4)), this.getRandomLocation(12L, random, cityList)
        ));
    }

    private Location getRandomLocation(long id, Random random, List<City> cityList) {
        return new Location(
                (id - 1), "Calle " + (random.nextInt(50) + 1),
                cityList.get(random.nextInt(cityList.size()))
        );
    }

    private void insertUser(AppUser user) {
        userDAO.insertLocation(user.getLocation());
        userDAO.insertUser(user);
        userDAO.setUserRole(user.getId(), user.getRoles());
    }

    public List<Long> getAppUsers() {
        return userDAO.getAppUserIds();
    }

    public void deleteAll() {
        userDAO.deleteUsers();
        userDAO.deleteRoles();
        userDAO.deleteLocations();
    }
}
