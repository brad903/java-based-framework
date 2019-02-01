package user.dao;

import core.jdbc.ConnectionManager;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateJdbcTemplate {

    public static void update(User user, UserDao userDao) throws SQLException {
        String sql = userDao.createQueryForUpdate();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            userDao.setValuesForUpdate(user, pstmt);
        }
    }
}
