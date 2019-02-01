package user.dao;

import core.jdbc.ConnectionManager;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertJdbcTemplate {

    public static void insert(User user, UserDao userDao) throws SQLException {
        String sql = userDao.createQueryForInsert();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            userDao.setValuesForInsert(user, pstmt);
        }
    }
}
