package user.dao;

import core.jdbc.ConnectionManager;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class UpdateJdbcTemplate {

    public void update(User user) throws SQLException {
        String sql = createQueryForUpdate();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValuesForUpdate(user, pstmt);
        }
    }

    abstract void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException;

    abstract String createQueryForUpdate();
}
