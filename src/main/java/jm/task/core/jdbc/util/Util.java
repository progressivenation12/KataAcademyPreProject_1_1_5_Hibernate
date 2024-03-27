package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static SessionFactory sessionFactory;

    public Util() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        Properties propertiesSettings = new Properties();

        propertiesSettings.put(Environment.URL, "jdbc:mysql://localhost:3306/kataacademy_task_1_1_4_jdbc");
        propertiesSettings.put(Environment.USER, "root");
        propertiesSettings.put(Environment.PASS, "root");
        propertiesSettings.put(Environment.SHOW_SQL, "true");
        propertiesSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

        configuration.setProperties(propertiesSettings);
        return configuration;
    }

    public static Connection getConnection() {
        final String databaseURL = "jdbc:mysql://localhost:3306/kataacademy_task_1_1_4_jdbc";
        final String databaseLogin = "root";
        final String databasePassword = "root";

        try {
            return DriverManager.getConnection(databaseURL, databaseLogin, databasePassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}