package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {}

    @Override
    public void createUsersTable() {
        String sqlQuery = """
                    CREATE TABLE Users (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        lastName VARCHAR(50) NOT NULL,
                        age TINYINT NOT NULL
                    );""";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE Users;";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();

            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());

            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        String sqlQuery = """
                DELETE FROM Users WHERE id = ?;
        """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = """
                    SELECT * FROM Users;     \s
        """;
        List<User> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                list.add(new User(
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getByte(4)
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sqlQuery = """
                DELETE FROM Users;
        """;

        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            statement.executeUpdate(sqlQuery);
            connection.commit();

        } catch (SQLException e1) {
            System.out.println(e1.getMessage());

            try {
                connection.rollback();
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println(e3.getMessage());
            }
        }
    }
}
