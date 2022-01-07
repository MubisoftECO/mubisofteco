package org.eco.mubisoft.data.user.dao;

import org.eco.mubisoft.data.user.model.*;
import org.eco.mubisoft.generator.connection.MySQLConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMysqlDAO implements UserDAO {

    private final MySQLConfig mySQLConfig = MySQLConfig.getInstance();

    @Override
    public void insertRoles(List<Role> userRoles) {
        String sqlQuery = "INSERT INTO role (id, name) VALUES (?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            for (Role role : userRoles) {
                pStm = connection.prepareStatement(sqlQuery);
                pStm.setLong(1, role.getId());
                pStm.setString(2, role.getName());
                pStm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public void setUserRole(Long userId, Collection<Long> roleIdList) {
        String sqlQuery = "INSERT INTO app_user_roles (app_user_id, roles_id) VALUES (?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            for (Long roleId : roleIdList) {
                pStm = connection.prepareStatement(sqlQuery);
                pStm.setLong(1, userId);
                pStm.setLong(2, roleId);
                pStm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public List<Role> getRoles() {
        List<Role> roleList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM role ORDER BY id";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            ResultSet set = pStm.executeQuery();

            while (set.next()) {
                roleList.add(new Role(
                        set.getLong(1),
                        set.getString(2)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
        return roleList;
    }

    @Override
    public void deleteRoles() {
        String sqlQuery = "DELETE FROM app_user_roles";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);

        sqlQuery = "DELETE FROM role";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public void insertCities(List<City> cities) {
        String sqlQueryCity = "INSERT INTO city (id, cd, city_code, name, province_id) VALUES (?, ?, ?, ?, ?)";
        String sqlQueryProvince = "INSERT INTO province (id, name, autonomous_community_id) VALUES (?, ?, ?)";
        String sqlQueryAC = "INSERT INTO autonomous_community (id, name, country) VALUES (?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            List<AutonomousCommunity> insertedAutonomousCommunities = new ArrayList<>();
            List<Province> insertedProvinces = new ArrayList<>();

            for (City city : cities) {
                try {
                    Province province = city.getProvince();
                    AutonomousCommunity autonomousCommunity = province.getAutonomousCommunity();

                    if (!insertedAutonomousCommunities.contains(autonomousCommunity)) {
                        // Autonomous Community
                        pStm = connection.prepareStatement(sqlQueryAC);
                        pStm.setLong(1, autonomousCommunity.getId());
                        pStm.setString(2, autonomousCommunity.getName());
                        pStm.setString(3, autonomousCommunity.getCountry());
                        pStm.executeUpdate();
                        insertedAutonomousCommunities.add(autonomousCommunity);
                    }
                    if (!insertedProvinces.contains(province)) {
                        // Province
                        pStm = connection.prepareStatement(sqlQueryProvince);
                        pStm.setLong(1, province.getId());
                        pStm.setString(2, province.getName());
                        pStm.setLong(3, province.getAutonomousCommunityID());
                        pStm.executeUpdate();
                        insertedProvinces.add(province);
                    }
                    // City
                    pStm = connection.prepareStatement(sqlQueryCity);
                    pStm.setLong(1, city.getId());
                    pStm.setInt(2, city.getCD());
                    pStm.setInt(3, city.getCityCode());
                    pStm.setString(4, city.getName());
                    pStm.setLong(5, city.getProvinceID());
                    pStm.executeUpdate();
                } catch (NullPointerException ignored) {}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public List<City> getCities() {
        List<City> cities = new ArrayList<>();
        String sqlQuery = "" +
                "SELECT c.id, c.city_code, c.cd, c.name, " +
                "       p.id, p.name, ac.id, ac.name " +
                "FROM city c " +
                "   JOIN province p on c.province_id = p.id" +
                "   JOIN autonomous_community ac on ac.id = p.autonomous_community_id";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            ResultSet set = pStm.executeQuery();

            while (set.next()) {
                cities.add(new City(
                        set.getLong(1),
                        new Province(
                                set.getLong(5),
                                new AutonomousCommunity(
                                        set.getLong(7),
                                        set.getString(8)
                                ),
                                set.getString(6)
                        ),
                        set.getInt(2),
                        set.getInt(3),
                        set.getString(4)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
        return cities;
    }

    @Override
    public void insertLocation(Location location) {
        String sqlQuery = "INSERT INTO location (id, street, city_id) VALUES (?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            pStm.setLong(1, location.getId());
            pStm.setString(2, location.getStreet());
            pStm.setLong(3, location.getCity());
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public void deleteLocations() {
        String sqlQuery = "DELETE FROM location";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);

        sqlQuery = "DELETE FROM city";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);

        sqlQuery = "DELETE FROM province";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);

        sqlQuery = "DELETE FROM autonomous_community";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public void insertUser(AppUser user) {
        String sqlQuery =
                "INSERT INTO app_user (id, name, second_name, username, password, location_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            pStm.setLong(1, user.getId());
            pStm.setString(2, user.getName());
            pStm.setString(3, user.getSecondName());
            pStm.setString(4, user.getUsername());
            pStm.setString(5, user.getPassword());
            pStm.setLong(6, user.getLocationID());
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public void deleteUsers() {
        String sqlQuery = "DELETE FROM app_user_roles";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);

        sqlQuery = "DELETE FROM app_user";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

}
