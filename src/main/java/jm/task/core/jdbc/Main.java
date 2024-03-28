package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Святослав", "Коротких", (byte) 20);
        userService.saveUser("Тихомир", "Белых", (byte) 25);
        userService.saveUser("Ратибор", "Красных", (byte) 31);
        userService.saveUser("Ярополк", "Черных", (byte) 38);

        userService.removeUserById(2);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}