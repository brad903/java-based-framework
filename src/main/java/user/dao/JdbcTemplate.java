package user.dao;

import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate {

    public void update(String sql, PreparedStatementSetter pstmtSetter) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
        }
    }

    public void update(String sql, Object...objects) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            createPreparedStatementSetter(objects).setValues(pstmt);
        }
    }

    private PreparedStatementSetter createPreparedStatementSetter(Object[] objects) {
        return pstmt -> {
            for (int i = 0; i < objects.length; i++) {
                pstmt.setObject(i + 1, objects[i]);
            }
            pstmt.executeUpdate();
        };
    }
}
