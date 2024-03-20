package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String USER_ID = "id";
    private static final String USER_TABLE = "user";
    private static final String USER_NAME = "name";
    private static final String USER_LASTNAME = "lastname";
    private static final String USER_AGE = "age";
    private static final Statement statement;

    static {
        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS " + USER_TABLE +
                "(id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "lastname VARCHAR(100) NOT NULL," +
                "age INT(150) NOT NULL)";

        try {
            Statement statement = Util.getConnection().createStatement();
            statement.execute(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS " + USER_TABLE;

        try {
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveData = "INSERT INTO " + USER_TABLE +
                "(" + USER_NAME + "," + USER_LASTNAME + "," + USER_AGE + ")" + " VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(saveData)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();

            System.out.printf("User с именем — %S добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeByID = "DELETE FROM " + USER_TABLE + " WHERE " + USER_ID + " = " + id;
        try {
            statement.executeUpdate(removeByID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT " + USER_ID + ", " + USER_NAME + ", " + USER_LASTNAME + ", " + USER_AGE + " FROM " + USER_TABLE);

            while (resultSet.next()) {
                User user = new User(resultSet.getString(USER_NAME), resultSet.getString(USER_LASTNAME), resultSet.getByte(USER_AGE));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE " + USER_TABLE;

        try {
            statement.execute(cleanTable);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
