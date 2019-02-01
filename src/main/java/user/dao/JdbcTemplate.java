package user.dao;

import com.sun.istack.internal.NotNull;
import core.jdbc.ConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class JdbcTemplate {
    private static final Logger log = getLogger(JdbcTemplate.class);

    public void update(String sql, PreparedStatementSetter pstmtSetter) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
        }
    }

    public void update(String sql, Object... objects) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            createPreparedStatementSetter(objects).setValues(pstmt);
        }
    }

    <T> T queryForObject(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
            T object = null;
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    object = rowMapper.mapRow(rs);
                }
            }
            return object;
        }
    }

    <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object ...objects) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            createPreparedStatementSetter(objects).setValues(pstmt);
            T object = null;
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    object = rowMapper.mapRow(rs);
                }
            }
            return object;
        }
    }

    <T> List<T> query(String sql, RowMapper<T> rowMapper, PreparedStatementSetter pstmtSetter) throws SQLException {
        List<T> users = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmtSetter.setValues(pstmt);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) users.add(rowMapper.mapRow(rs));
            }
        }
        return users;
    }

    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... objects) throws SQLException {
        List<T> users = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) users.add(rowMapper.mapRow(rs));
            }
        }
        return users;
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
