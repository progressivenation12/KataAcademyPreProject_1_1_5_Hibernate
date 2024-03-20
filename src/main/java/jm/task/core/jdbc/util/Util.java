package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    public static Connection getConnection(){
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream("src/main/resources/sql.properties")) {
            properties.load(inputStream);

            return DriverManager.getConnection(
                    properties.getProperty("database.url"),
                    properties.getProperty("database.login"),
                    properties.getProperty("database.pass"));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}