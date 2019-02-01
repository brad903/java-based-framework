package user.dao;

import core.jdbc.ConnectionManager;
import org.slf4j.Logger;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class UserDao {
    private static final Logger log = getLogger(UserDao.class);

    public void insert(User user) throws SQLException {
        new JdbcTemplate() {
            @Override
            String createQuery() {
                return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getUserId());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getName());
                pstmt.setString(4, user.getEmail());
                pstmt.executeUpdate();
            }
        }.execute();
    }

    public void update(User user) throws SQLException {
        new JdbcTemplate() {
            @Override
            String createQuery() {
                return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, user.getPassword());
                pstmt.setString(2, user.getName());
                pstmt.setString(3, user.getEmail());
                pstmt.setString(4, user.getUserId());
                pstmt.executeUpdate();
            }
        }.execute();
    }

    public List<User> findAll() throws SQLException {
        return new SelectJdbcTemplate() {
            @Override
            String createQuery() {
                return "SELECT * FROM USERS";
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
            }

        }.query();
    }

    public User findByUserId(String userId) throws SQLException {
        return (User) new SelectJdbcTemplate() {
            @Override
            String createQuery() {
                return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            }

            @Override
            Object mapRow(ResultSet rs) throws SQLException {
                return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
            }

            @Override
            void setValues(PreparedStatement pstmt) throws SQLException {
                pstmt.setString(1, userId);
            }
        }.queryForObject();
    }
}

