package mate.academy.internetshop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.internetshop.controllers.RegistrationController;
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
            Connection connection = DriverManager.getConnection(url, connectionProps);
            return connection;
        } catch (SQLException e) {
            LOGGER.warn("Can't established connection to DB", e);
            throw new RuntimeException("Can't established connection to DB", e);
        }
    }
}
