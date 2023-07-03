package me.shreyeschekuru.dsa.data.service;

import me.shreyeschekuru.dsa.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Service
public class UserRegistrationService {
    private final DataSource dataSource;

    @Autowired
    public UserRegistrationService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean registerUser(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO app_user (" +
                    "first_name, last_name, email, password, username, mobile, created_at, updated_at, locked, enabled) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());

            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getUsername());

            statement.setString(6, user.getMobile());

            LocalDateTime dateTime = LocalDateTime.now();

            statement.setObject(7, dateTime);
            statement.setObject(8, dateTime);
            statement.setBoolean(9, false);
            statement.setBoolean(10, true);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
