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
        String sql = createQueryForInsert();

        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValuesForInsert(user, pstmt);
        }
    }

    private String createQueryForInsert() {
        return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    }

    public void update(User user) throws SQLException {
        String sql = createQueryForUpdate();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValuesForUpdate(user, pstmt);
        }
    }

    private String createQueryForUpdate() {
        return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
    }

    private void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.executeUpdate();
    }

    private void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUserId());
        pstmt.executeUpdate();
    }

    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            try(ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()) {
                    User user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
                    users.add(user);
                    log.debug("user : {}", user);
                }
            }
        }
        return users;
    }

    public User findByUserId(String userId) throws SQLException {
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userId);

            User user = null;
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
                }
            }

            return user;
        }
    }
}

