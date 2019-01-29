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
import java.util.function.Function;
import java.util.function.Supplier;

import static org.slf4j.LoggerFactory.getLogger;

public class UserDao {
    private static final Logger log = getLogger(UserDao.class);

    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.executeUpdate();
        }
    }

    public void update(User user) throws SQLException {
        // TODO 구현 필요함.
    }

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
        return new ArrayList<User>();
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

