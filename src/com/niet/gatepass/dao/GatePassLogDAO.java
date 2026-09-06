package com.niet.gatepass.dao;

import com.niet.gatepass.model.GatePassLog;
import com.niet.gatepass.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GatePassLogDAO {

    public int addLog(int passId, String action, String verifiedBy, String remarks) throws SQLException {
        String sql = "INSERT INTO gate_pass_logs (pass_id, action, verified_by, remarks) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, passId);
            ps.setString(2, action);
            ps.setString(3, verifiedBy);
            ps.setString(4, remarks);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public List<GatePassLog> getLogsByPass(int passId) throws SQLException {
        List<GatePassLog> list = new ArrayList<>();
        String sql = "SELECT * FROM gate_pass_logs WHERE pass_id = ? ORDER BY action_time";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, passId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GatePassLog l = new GatePassLog();
                    l.setLogId(rs.getInt("log_id"));
                    l.setPassId(rs.getInt("pass_id"));
                    l.setAction(rs.getString("action"));
                    l.setActionTime(rs.getTimestamp("action_time"));
                    l.setVerifiedBy(rs.getString("verified_by"));
                    l.setRemarks(rs.getString("remarks"));
                    list.add(l);
                }
            }
        }
        return list;
    }
}
