package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exeptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT users.user_id, users.name, users.login, "
                + "users.password, users.salt, roles.role_name "
                + "FROM users "
                + "JOIN users_roles "
                + "ON users.user_id = users_roles.user_id "
                + "JOIN roles "
                + "ON users_roles.role_id = roles.role_id "
                + "WHERE login = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with login: " + login);
        }
        return Optional.empty();
    }

    @Override
    public User create(User element) {
        String query = "INSERT INTO users (name, login, password, salt) VALUE (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.setBytes(4, element.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                element.setId(resultSet.getLong(1));
                setUserRole(element);
            }
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new user");
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT users.user_id, users.name, users.login, "
                + "users.password, users.salt, roles.role_name "
                + "FROM users "
                + "JOIN users_roles "
                + "ON users.user_id = users_roles.user_id "
                + "JOIN roles "
                + "ON users_roles.role_id = roles.role_id "
                + "WHERE users.user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with id: " + id);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT users.user_id, users.name, users.login, "
                + "users.password, users.salt, roles.role_name "
                + "FROM users "
                + "JOIN users_roles "
                + "ON users.user_id = users_roles.user_id "
                + "JOIN roles "
                + "ON users_roles.role_id = roles.role_id";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet).get());
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't established connection to DB");
        }
    }

    @Override
    public User update(User element) {
        String query = "UPDATE users "
                + "SET name = ?, login = ?, password = ? "
                + "WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(4, element.getId());
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.executeUpdate();
            return getUserFromResultSet(statement.executeQuery()).get();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product with id: " + element.getId());
        }
    }

    @Override
    public boolean delete(Long id) {
        String query2 = "DELETE FROM users_roles WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query2);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id: " + id);
        }
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id: " + id);
        }
        return true;
    }

    public Optional<User> getUserFromResultSet(ResultSet resultSet) throws SQLException {
        String query = "SELECT roles.role_name "
                + "FROM users_roles "
                + "INNER JOIN roles "
                + "ON users_roles.role_id = roles.role_id "
                + "WHERE users_roles.user_id = ?";
        Set<Role> roleName = new HashSet<>();
        long userId = resultSet.getLong("user_id");
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSetOfRoles = statement.executeQuery();
            while (resultSetOfRoles.next()) {
                String name = resultSetOfRoles.getString("role_name");
                roleName.add(Role.of(name));
            }
        }
        String userName = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        User user = new User(userName, login, password, salt);
        user.setId(userId);
        user.setRoles(roleName);
        return Optional.of(user);
    }

    public void setUserRole(User user) {
        String query = "INSERT INTO users_roles (user_id, role_id) VALUE (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            statement.setLong(2, 2);
            statement.executeUpdate();
            return;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't set role to the user with id: "
                    + user.getId());
        }
    }
}
