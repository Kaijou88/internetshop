package com.project.internetshop.util;

import com.project.internetshop.controllers.RegistrationController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConnectionUtil {
    private static final Logger LOGGER = Logger.getLogger(RegistrationController.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.warn("Can't find MySQL Driver", e);
            throw new RuntimeException("Can't find MySQL Driver");
        }
    }

    public static Connection getConnection() {

        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "1234");

        String url = "jdbc:mysql://localhost:3306/internetshop";
        try {
            return DriverManager.getConnection(url, connectionProps);
        } catch (SQLException e) {
            LOGGER.warn("Can't established connection to DB", e);
            throw new RuntimeException("Can't established connection to DB", e);
        }
    }
}
