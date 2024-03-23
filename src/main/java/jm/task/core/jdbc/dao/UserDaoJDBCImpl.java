package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS user" +
                "(id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(100) NOT NULL," +
                "lastname VARCHAR(100) NOT NULL," +
                "age INT(150) NOT NULL)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropTable = "DROP TABLE IF EXISTS user";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveData = "INSERT INTO user(name, lastname, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(saveData)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // PreparedStatement предпочтительнее использовать при параметризованных запросах,
    // так как это повышает безопасность (нет необходимости конкатенировать строку запроса с параметрами),
    // производительность
    // (preparedStatement компилируется один раз и может использоваться многократно,
    // в нашем случае используется один раз при удалении по id),
    // читаемость кода
    public void removeUserById(long id) {
        String removeByID = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeByID)) {
            preparedStatement.setLong(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT name, lastname, age FROM user");

            while (resultSet.next()) {
                User user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getByte(3));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE user";

        try (Statement statement = connection.createStatement()) {
            statement.execute(cleanTable);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
