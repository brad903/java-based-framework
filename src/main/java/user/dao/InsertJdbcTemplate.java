package user.dao;

import core.jdbc.ConnectionManager;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class InsertJdbcTemplate {

    public void insert(User user) throws SQLException {
        String sql = createQueryForInsert();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValuesForInsert(user, pstmt);
        }
    }

    abstract void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException;

    abstract String createQueryForInsert();
}
