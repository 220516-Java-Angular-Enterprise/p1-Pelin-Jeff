package com.revature.ers.daos;

import com.revature.ers.models.UserRole;
import com.revature.ers.util.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserRoleDAO implements CrudDAO<UserRole> {
    @Override
    public void save(UserRole obj) {
            try (Connection con = ConnectionFactory.getInstance().getConnection()) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO user_roles(role_id, roles) VALUES (?, ?)");
                ps.setString(1, obj.getId());
                ps.setString(2, obj.getRole());
                ps.executeUpdate();

            }   catch (SQLException e) {
                throw new RuntimeException("An error occurred when tyring to save to the database.");
            }
    }

    @Override
    public void update(UserRole obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public UserRole getById(String id) {
        return null;
    }

    @Override
    public List<UserRole> getAll() {
        return null;
    }
}
