package com.niet.gatepass.dao;

import com.niet.gatepass.model.Student;
import com.niet.gatepass.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    /** Registers a new student with status = PENDING. Returns generated student_id. */
    public int registerStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students (name, roll_no, email, password_hash, hostel_id, room_no, contact_no, emergency_contact, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'PENDING')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getRollNo());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPasswordHash());
            ps.setInt(5, s.getHostelId());
            ps.setString(6, s.getRoomNo());
            ps.setString(7, s.getContactNo());
            ps.setString(8, s.getEmergencyContact());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public Student getStudentByEmail(String email) throws SQLException {
        String sql = "SELECT s.*, h.hostel_name FROM students s LEFT JOIN hostels h ON s.hostel_id = h.hostel_id WHERE s.email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public Student getStudentById(int studentId) throws SQLException {
        String sql = "SELECT s.*, h.hostel_name FROM students s LEFT JOIN hostels h ON s.hostel_id = h.hostel_id WHERE s.student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    /** Students awaiting registration approval for a given hostel (warden view). */
    public List<Student> getPendingStudentsByHostel(int hostelId) throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT s.*, h.hostel_name FROM students s LEFT JOIN hostels h ON s.hostel_id = h.hostel_id " +
                     "WHERE s.status = 'PENDING' AND s.hostel_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hostelId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        }
        return list;
    }

    public boolean updateStatus(int studentId, String status) throws SQLException {
        String sql = "UPDATE students SET status = ? WHERE student_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, studentId);
            return ps.executeUpdate() > 0;
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setRollNo(rs.getString("roll_no"));
        s.setEmail(rs.getString("email"));
        s.setPasswordHash(rs.getString("password_hash"));
        s.setHostelId(rs.getInt("hostel_id"));
        s.setHostelName(rs.getString("hostel_name"));
        s.setRoomNo(rs.getString("room_no"));
        s.setContactNo(rs.getString("contact_no"));
        s.setEmergencyContact(rs.getString("emergency_contact"));
        s.setStatus(rs.getString("status"));
        s.setCreatedAt(rs.getTimestamp("created_at"));
        return s;
    }
}
