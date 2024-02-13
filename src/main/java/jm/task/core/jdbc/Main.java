package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Daniil", "Khokhlov", (byte) 23);
        userService.saveUser("Kirill", "Golouhov", (byte) 23);
        userService.saveUser("Ilya", "Ageev", (byte) 23);
        userService.saveUser("Georgiy", "Bogoslavcev", (byte) 23);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();

        try {
            Util.closeConnection();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
