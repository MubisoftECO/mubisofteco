package org.eco.mubisoft.generator.connection;

import java.sql.*;

/**
 * Class to reuse repetitive MySQL tasks. It is a singleton class so
 * database.properties is only loaded once.
 */
public class MySQLConfig {

    private static MySQLConfig myconfig;

    private String username;
    private String password;
    private String connectionString;

    /**
     * Singleton pattern. getInstance() must be static, otherwise it cannot be
     * called (as constructor is private).
     */
    public static MySQLConfig getInstance() {
        if (myconfig == null) {
            myconfig = new MySQLConfig();
        }
        return myconfig;
    }

    /**
     * Private constructor. If someone wants the instance, it should call
     * MySQLConfig.getInstance() That way the constructor is called only once.
     */
    private MySQLConfig() {
        /*
        Properties prop = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try {
            // Load connection properties
            prop.load(classLoader.getResourceAsStream("db.properties"));

            // Set the connection properties
            username = prop.getProperty("username");
            password = prop.getProperty("password");
            connectionString =
                    prop.getProperty("url") +
                    prop.getProperty("serverName") + ":" +
                    prop.getProperty("port") + "/" +
                    prop.getProperty("dataBaseName") +
                    "?serverTimezone=" + prop.getProperty("timezone");
        } catch (IOException e) {
            e.printStackTrace();
        }
         */
        username = "mubisoft_admin";
        password = "admin@mubisoft";
        connectionString = "jdbc:mysql://localhost:3306/mubisoft_eco?serverTimezone=Europe/Madrid";
    }

    /**
     * Get a new MySQL Connection.
     */
    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Connection Driver Error");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could Not Connect to DB ");
        }
        return connection;
    }

    /**
     * Close a MySQL Connection.
     */
    public void disconnect(Connection connection, Statement statement) {
        try {
            if (statement != null) {
                statement.clearWarnings();
                statement.close();
            }
            if (connection != null) {
                connection.clearWarnings();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error disconnecting");
        }
    }

    public static void executeDelete(MySQLConfig mySQLConfig, String sqlQuery) {
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }
}
