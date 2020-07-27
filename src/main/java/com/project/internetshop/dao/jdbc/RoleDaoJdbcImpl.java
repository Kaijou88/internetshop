package com.project.internetshop.dao.jdbc;

import com.project.internetshop.dao.RoleDao;
import com.project.internetshop.exeptions.DataProcessingException;
import com.project.internetshop.lib.Dao;
import com.project.internetshop.model.Role;
import com.project.internetshop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Dao
public class RoleDaoJdbcImpl implements RoleDao {
    @Override
    public Role create(Role role) {
        String query = "INSERT INTO roles (role_name) VALUE (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, role.getRoleName().name());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            return role;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new role");
        }
    }
}
