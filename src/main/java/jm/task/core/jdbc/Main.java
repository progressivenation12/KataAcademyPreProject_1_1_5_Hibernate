package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Святослав", "Коротких", (byte) 20);
        userDao.saveUser("Тихомир", "Белых", (byte) 25);
        userDao.saveUser("Ратибор", "Красных", (byte) 31);
        userDao.saveUser("Ярополк", "Черных", (byte) 38);

        userDao.removeUserById(1);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
