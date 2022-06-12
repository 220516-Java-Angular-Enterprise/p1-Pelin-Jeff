package com.revature.ers.daos;

import com.revature.ers.models.User;
import com.revature.ers.util.custom_exceptions.InvalidSQLException;
import com.revature.ers.util.database.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CrudDAO<User> {
/*    Connection con = DatabaseConnection.getCon();
    String path = "src/main/resources/database/user.txt";*/

    @Override
    public void save(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (user_id, username, email, passwords, given_name, surname, is_active, role_id) " +
                    "                                       VALUES (?, ?, ?, crypt(?, gen_salt('bf')), ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getGiven_name());
            ps.setString(6, obj.getSurname());
            ps.setBoolean(7, obj.is_active());
            ps.setString(8, obj.getRole());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to save to the database.");
        }
    }

    @Override
    public void update(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET username = ?," +
                                    ", email = ?, passwords = crypt(?, gen_salt('bf')), given_name = ?," +
                                    "surname = ?, is_active = ?, role_id = ? WHERE id = ?");
            ps.setString(1, obj.getUsername());
            ps.setString(2, obj.getEmail());
            ps.setString(3, obj.getPassword());
            ps.setString(4, obj.getGiven_name());
            ps.setString(5, obj.getSurname());
            ps.setBoolean(6, obj.is_active());
            ps.setString(7, obj.getRole());
            ps.setString(8, obj.getId());
            ps.executeUpdate();
        }   catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to update the database.");
        }

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public User getById(String id) {
        User user = null;

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getString("user_id"),
                        rs.getString("username"), rs.getString("email"),
                        rs.getString("passwords"), rs.getString("given_name"),
                        rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to get data from to the database.");
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("passwords"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getBoolean("is_active"),
                        rs.getString("role_id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to get data from to the database.");
        }

        return users;
    }

    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT username FROM users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // String username = rs.getString("username");
                // usernames.add(username);

                usernames.add(rs.getString("username").toLowerCase());
            }
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when tyring to get data from to the database.");
        }

        return usernames;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = crypt(?, password)");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user = new User(rs.getString("user_id"), rs.getString("username"),
                        rs.getString("email"), rs.getString("passwords"), rs.getString("given_name"),
                        rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
            }
        }   catch (SQLException e) {
                throw new InvalidSQLException("An error occurred when tyring to get data from to the database.");
        }

        return user;
    }

    public List getUsersByUsername(String name) {
        List<User> users = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username LIKE ?");
            ps.setString(1, name +'%');
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                users.add(new User(rs.getString("user_id"), rs.getString("username"), rs.getString("passwords"), rs.getString("role_id")));
            }
        }   catch (SQLException e) {
            throw new InvalidSQLException("An error occurred when tyring to get data from to the database.");
        }
        return users;
    }

}

