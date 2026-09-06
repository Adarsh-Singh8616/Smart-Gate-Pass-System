package com.niet.gatepass.dao;

import com.niet.gatepass.model.GatePass;
import com.niet.gatepass.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GatePassDAO {

    private static final String BASE_SELECT =
        "SELECT gp.*, s.name AS student_name, s.roll_no, s.room_no, h.hostel_name " +
        "FROM gate_pass gp " +
        "JOIN students s ON gp.student_id = s.student_id " +
        "LEFT JOIN hostels h ON s.hostel_id = h.hostel_id ";

    /** Student applies for a new gate pass. Returns generated pass_id. */
    public int createGatePass(GatePass gp) throws SQLException {
        String sql = "INSERT INTO gate_pass " +
                "(pass_code, student_id, destination, reason, out_date, out_time, expected_return, emergency_contact, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'PENDING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, gp.getPassCode());
            ps.setInt(2, gp.getStudentId());
            ps.setString(3, gp.getDestination());
            ps.setString(4, gp.getReason());
            ps.setDate(5, gp.getOutDate());
            ps.setTime(6, gp.getOutTime());
            ps.setTimestamp(7, gp.getExpectedReturn());
            ps.setString(8, gp.getEmergencyContact());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public GatePass getById(int passId) throws SQLException {
        String sql = BASE_SELECT + " WHERE gp.pass_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, passId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public GatePass getByPassCode(String passCode) throws SQLException {
        String sql = BASE_SELECT + " WHERE gp.pass_code = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, passCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /** All gate-pass requests submitted by a given student, most recent first. */
    public List<GatePass> getByStudent(int studentId) throws SQLException {
        List<GatePass> list = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE gp.student_id = ? ORDER BY gp.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** Pending requests for a warden, scoped to their hostel. */
    public List<GatePass> getPendingForWarden(int hostelId) throws SQLException {
        List<GatePass> list = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE gp.status = 'PENDING' AND s.hostel_id = ? ORDER BY gp.created_at";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hostelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** All requests for a hostel, filtered by status (for warden tabs: pending/approved/rejected). */
    public List<GatePass> getByHostelAndStatus(int hostelId, String status) throws SQLException {
        List<GatePass> list = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE s.hostel_id = ? AND gp.status = ? ORDER BY gp.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hostelId);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    /** Requests approved by warden, awaiting chief warden's final decision. */
    public List<GatePass> getPendingForChiefWarden() throws SQLException {
        List<GatePass> list = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE gp.status = 'WARDEN_APPROVED' ORDER BY gp.created_at";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        }
        return list;
    }

    /** All requests for the chief warden's dashboard, filtered by status. */
    public List<GatePass> getAllByStatus(String status) throws SQLException {
        List<GatePass> list = new ArrayList<>();
        String sql = BASE_SELECT + " WHERE gp.status = ? ORDER BY gp.created_at DESC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public boolean updateStatus(int passId, String status, String remarksColumn, String remarks) throws SQLException {
        String sql = "UPDATE gate_pass SET status = ?, " + remarksColumn + " = ? WHERE pass_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, remarks);
            ps.setInt(3, passId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateStatus(int passId, String status) throws SQLException {
        String sql = "UPDATE gate_pass SET status = ? WHERE pass_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, passId);
            return ps.executeUpdate() > 0;
        }
    }

    private GatePass mapRow(ResultSet rs) throws SQLException {
        GatePass gp = new GatePass();
        gp.setPassId(rs.getInt("pass_id"));
        gp.setPassCode(rs.getString("pass_code"));
        gp.setStudentId(rs.getInt("student_id"));
        gp.setStudentName(rs.getString("student_name"));
        gp.setRollNo(rs.getString("roll_no"));
        gp.setRoomNo(rs.getString("room_no"));
        gp.setHostelName(rs.getString("hostel_name"));
        gp.setDestination(rs.getString("destination"));
        gp.setReason(rs.getString("reason"));
        gp.setOutDate(rs.getDate("out_date"));
        gp.setOutTime(rs.getTime("out_time"));
        gp.setExpectedReturn(rs.getTimestamp("expected_return"));
        gp.setEmergencyContact(rs.getString("emergency_contact"));
        gp.setStatus(rs.getString("status"));
        gp.setWardenRemarks(rs.getString("warden_remarks"));
        gp.setChiefWardenRemarks(rs.getString("chief_warden_remarks"));
        gp.setCreatedAt(rs.getTimestamp("created_at"));
        gp.setUpdatedAt(rs.getTimestamp("updated_at"));
        return gp;
    }
}
