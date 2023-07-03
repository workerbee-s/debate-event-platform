package me.shreyeschekuru.dsa.data.service;

import me.shreyeschekuru.dsa.data.entity.User;
import me.shreyeschekuru.dsa.data.repository.UserRepository;
import me.shreyeschekuru.dsa.utilities.UIUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    private final DataSource dataSource;
    private final UserRepository repository;

    @Autowired
    public UserService(DataSource dataSource, UserRepository repository) {
        this.dataSource = dataSource;
        this.repository = repository;
    }

    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public boolean updateRole(String email, String roleName){
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE app_user SET " +
                    "assigned_role = ?, " +
                    "updated_at = ? " +
                    "WHERE email = ?";

            LocalDateTime dateTime = LocalDateTime.now();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, roleName);
            statement.setObject(2, dateTime);
            statement.setString(3, email);

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(User user) {

        System.out.println(user.toString());

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO app_user (" +
                    "first_name, last_name, email, password, username, mobile, created_at, updated_at, locked, enabled, password_applicable, oauth_provider, email_verified, profile_picture) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            statement.setBoolean(11, user.isPasswordApplicable());
            statement.setString(12, user.getoAuthProvider());
            statement.setBoolean(13, user.isEmailVerified());
            statement.setString(14, user.getProfilePicture());

            if (findUserByEmail(user.getEmail()) != null) {
                System.out.println("User already exists");
                return false;
            }

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String email, String password){

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT first_name, last_name, email, username, password FROM app_user WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedEncryptedPassword = resultSet.getString("password");
                        String encryptedSuppliedPassword = UIUtility.hashPassword(password);
                        System.out.println(storedEncryptedPassword + "  :  " + encryptedSuppliedPassword);
                        return storedEncryptedPassword.equals(encryptedSuppliedPassword);
                    }
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User findUserByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM app_user WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUsername(resultSet.getString("username"));
                user.setMobile(resultSet.getString("mobile"));
                user.setAssignedRole(resultSet.getString("assigned_role"));
                user.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                user.setUpdatedAt(resultSet.getObject("updated_at", LocalDateTime.class));
                user.setLocked(resultSet.getBoolean("locked"));
                user.setEnabled(resultSet.getBoolean("enabled"));
                user.setoAuthProvider(resultSet.getString("oauth_provider"));
                user.setEmailVerified(resultSet.getBoolean("email_verified"));
                user.setPasswordApplicable(resultSet.getBoolean("password_applicable"));
                user.setProfilePicture(resultSet.getString("profile_picture"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }

}
