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

public abstract class SelectJdbcTemplate {
    private static final Logger log = getLogger(SelectJdbcTemplate.class);

    List<User> query(String sql) throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    users.add((User) mapRow(rs));
                }
            }
        }
        return users;
    }

    Object queryForObject(String sql) throws SQLException {
        try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            setValues(pstmt);

            Object object = null;
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    object = mapRow(rs);
                }
            }

            return object;
        }
    }

    abstract Object mapRow(ResultSet rs) throws SQLException;

    abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
