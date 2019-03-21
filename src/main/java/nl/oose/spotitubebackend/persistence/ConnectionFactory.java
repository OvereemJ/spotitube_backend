package nl.oose.spotitubebackend.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionFactory {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/spotitube?serverTimezone=UTC";
    private static final String USER_DD = "spotitube";
    public static final String PASSWORD = "spotitube";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private Properties properties;

    public ConnectionFactory(){
        properties = getProperties();
    }

    private Properties getProperties(){
        Properties properties = new Properties();
        String path = getClass().getResource("").getPath() + "database.properties";

        try {

            FileInputStream fileInputStream = new FileInputStream(path);
            properties.load(fileInputStream);
        } catch (IOException e) {
            properties.setProperty("db.user", USER_DD);
            properties.setProperty("db.password", PASSWORD);
            properties.setProperty("db.url", CONNECTION_URL);
            properties.setProperty("db.driver", DRIVER);

            e.printStackTrace();
        }
        return properties;
    }

    public Connection getConnection(){
        loadDriver();
        try {
            Connection connection = DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.user"),
                    properties.getProperty("db.password")
            );
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadDriver(){
        try {
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}