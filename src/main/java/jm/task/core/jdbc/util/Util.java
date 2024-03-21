package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() {
        final String databaseURL = "jdbc:mysql://localhost:3306/kataacademy_task_1_1_4_jdbc";
        final String databaseLogin = "root";
        final String databasePassword = "Rfhfctd@Hjvfy23";

        try {
            return DriverManager.getConnection(databaseURL, databaseLogin, databasePassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}