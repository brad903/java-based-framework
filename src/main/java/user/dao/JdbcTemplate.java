package user.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JdbcTemplate {

    public void execute() throws SQLException {
        String sql = createQuery();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValues(pstmt);
        }
    }

    abstract void setValues(PreparedStatement pstmt) throws SQLException;

    abstract String createQuery();
}
