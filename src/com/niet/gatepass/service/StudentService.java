package com.niet.gatepass.service;

import com.niet.gatepass.dao.StudentDAO;
import com.niet.gatepass.model.Student;
import com.niet.gatepass.util.PasswordUtil;

import java.sql.SQLException;

public class StudentService {

    private final StudentDAO studentDAO = new StudentDAO();

    public static class RegistrationResult {
        public boolean success;
        public String message;
    }

    /** Validates and registers a new student account with status = PENDING. */
    public RegistrationResult register(String name, String rollNo, String email, String password,
                                        int hostelId, String roomNo, String contactNo, String emergencyContact) throws SQLException {
        RegistrationResult result = new RegistrationResult();

        if (name == null || name.trim().isEmpty() ||
            rollNo == null || rollNo.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.length() < 6) {
            result.success = false;
            result.message = "Please fill all required fields. Password must be at least 6 characters.";
            return result;
        }

        if (studentDAO.getStudentByEmail(email) != null) {
            result.success = false;
            result.message = "An account with this email already exists.";
            return result;
        }

        Student s = new Student();
        s.setName(name.trim());
        s.setRollNo(rollNo.trim());
        s.setEmail(email.trim());
        s.setPasswordHash(PasswordUtil.hash(password));
        s.setHostelId(hostelId);
        s.setRoomNo(roomNo);
        s.setContactNo(contactNo);
        s.setEmergencyContact(emergencyContact);

        int id = studentDAO.registerStudent(s);
        result.success = id > 0;
        result.message = result.success
                ? "Registration submitted. Your account is pending warden approval."
                : "Registration failed. Please try again.";
        return result;
    }

    public Student getProfile(int studentId) throws SQLException {
        return studentDAO.getStudentById(studentId);
    }

    /** Warden approves/rejects a pending student registration. */
    public boolean decideRegistration(int studentId, boolean approve) throws SQLException {
        return studentDAO.updateStatus(studentId, approve ? "ACTIVE" : "REJECTED");
    }
}
