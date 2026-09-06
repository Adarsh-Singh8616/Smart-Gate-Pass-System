package com.niet.gatepass.dao;

import com.niet.gatepass.model.ChiefWarden;
import com.niet.gatepass.util.DBConnection;

import java.sql.*;

public class ChiefWardenDAO {

    public ChiefWarden getChiefWardenByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM chief_wardens WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ChiefWarden cw = new ChiefWarden();
                    cw.setCwId(rs.getInt("cw_id"));
                    cw.setName(rs.getString("name"));
                    cw.setEmail(rs.getString("email"));
                    cw.setPasswordHash(rs.getString("password_hash"));
                    return cw;
                }
            }
        }
        return null;
    }
}
