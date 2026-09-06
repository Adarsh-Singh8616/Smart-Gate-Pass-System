package com.niet.gatepass.dao;

import com.niet.gatepass.model.Warden;
import com.niet.gatepass.util.DBConnection;

import java.sql.*;

public class WardenDAO {

    public Warden getWardenByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM wardens WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Warden w = new Warden();
                    w.setWardenId(rs.getInt("warden_id"));
                    w.setName(rs.getString("name"));
                    w.setEmail(rs.getString("email"));
                    w.setPasswordHash(rs.getString("password_hash"));
                    w.setHostelId(rs.getInt("hostel_id"));
                    return w;
                }
            }
        }
        return null;
    }
}
