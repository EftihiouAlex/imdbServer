package com.imdb.finalproject.dao;

import com.imdb.finalproject.model.User;
import com.imdb.finalproject.service.dbutil.DBUtil;
import com.imdb.finalproject.service.exceptions.EmailAlreadyExistsException;
import com.imdb.finalproject.service.exceptions.UserNotFoundException;
import com.imdb.finalproject.service.exceptions.UsernameAlreadyExistsException;

import javax.ws.rs.ext.Provider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implements the IUserDao interface.
 * */
@Provider
public class UserDAOImpl implements IUserDAO {


    @Override
    public User insertUser(User user) throws EmailAlreadyExistsException, UsernameAlreadyExistsException {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        int autoRegeneratedId = 0;
        User insertedUser = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();

            if (isDuplicateUsername(conn, user.getUsername())) {
                throw new UsernameAlreadyExistsException("Username already exists");
            }

            if (isDuplicateEmail(conn, user.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists");
            }

            PreparedStatement p = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            String username = user.getUsername();
            String password = user.getPassword();
            String email = user.getEmail();

            p.setString(1, username);
            p.setString(2, password);
            p.setString(3, email);

            p.executeUpdate();

            ResultSet regeneratedKeys = p.getGeneratedKeys();
            if (regeneratedKeys.next()) {
                autoRegeneratedId = regeneratedKeys.getInt(1);
            }


            insertedUser = new User();
            insertedUser.setId(autoRegeneratedId);
            insertedUser.setUsername(user.getUsername());
            insertedUser.setPassword(user.getPassword());
            insertedUser.setEmail(user.getEmail());


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
        }
        return insertedUser;
    }

    @Override
    public User getUser(String username, String password) throws UserNotFoundException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        ResultSet rs;
        User user = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            PreparedStatement p = conn.prepareStatement(sql);


            p.setString(1, username);
            p.setString(2, password);


            rs = p.executeQuery();

            if (rs != null && rs.next()) {
                user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new UserNotFoundException(e.getMessage());
        } finally {
            DBUtil.closeConnection(conn);
        }
        return user;
    }

    private boolean isDuplicateUsername(Connection conn, String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        PreparedStatement p = conn.prepareStatement(sql);
        p.setString(1, username);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }

    private boolean isDuplicateEmail(Connection conn, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        PreparedStatement p = conn.prepareStatement(sql);
        p.setString(1, email);
        ResultSet rs = p.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
}
