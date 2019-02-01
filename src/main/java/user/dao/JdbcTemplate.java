package user.dao;

import core.jdbc.ConnectionManager;
import user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {

    public void execute(User user) throws SQLException {
        String sql = createQuery();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValues(user, pstmt);
        }
    }

    abstract void setValues(User user, PreparedStatement pstmt) throws SQLException;

    abstract String createQuery();
}
