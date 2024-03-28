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
    private static final String DB_URL = "jdbc:mysql://localhost:3306/kataacademy_db";
    private static final String BD_LOGIN = "root";
    private static final String DB_PASS = "root";
    private static final String DB_SHOW_SQL = "true";
    private static  final String DB_DIALECT = "org.hibernate.dialect.MySQL5Dialect";

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

        propertiesSettings.put(Environment.URL, DB_URL);
        propertiesSettings.put(Environment.USER, BD_LOGIN);
        propertiesSettings.put(Environment.PASS, DB_PASS);
        propertiesSettings.put(Environment.SHOW_SQL, DB_SHOW_SQL);
        propertiesSettings.put(Environment.DIALECT, DB_DIALECT);

        configuration.setProperties(propertiesSettings);
        return configuration;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, BD_LOGIN, DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}