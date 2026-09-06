package com.niet.gatepass.service;

import com.niet.gatepass.dao.GatePassDAO;
import com.niet.gatepass.model.GatePass;
import com.niet.gatepass.util.PassCodeGenerator;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class GatePassService {

    private final GatePassDAO gatePassDAO = new GatePassDAO();

    public static class ApplyResult {
        public boolean success;
        public String message;
        public String passCode;
    }

    /**
     * Validates and submits a new gate pass application on behalf of a student.
     * Dates/times are expected as yyyy-MM-dd, HH:mm and yyyy-MM-dd HH:mm respectively.
     */
    public ApplyResult apply(int studentId, String destination, String reason,
                              String outDateStr, String outTimeStr, String expectedReturnStr,
                              String emergencyContact) {
        ApplyResult result = new ApplyResult();

        if (isBlank(destination) || isBlank(reason) || isBlank(outDateStr) ||
            isBlank(outTimeStr) || isBlank(expectedReturnStr) || isBlank(emergencyContact)) {
            result.success = false;
            result.message = "All fields are required.";
            return result;
        }

        try {
            GatePass gp = new GatePass();
            gp.setStudentId(studentId);
            gp.setDestination(destination.trim());
            gp.setReason(reason.trim());
            gp.setOutDate(Date.valueOf(outDateStr));
            gp.setOutTime(Time.valueOf(outTimeStr.length() == 5 ? outTimeStr + ":00" : outTimeStr));
            gp.setExpectedReturn(Timestamp.valueOf(expectedReturnStr.replace("T", " ") +
                    (expectedReturnStr.length() == 16 ? ":00" : "")));
            gp.setEmergencyContact(emergencyContact.trim());
            gp.setPassCode(PassCodeGenerator.generate());

            int id = gatePassDAO.createGatePass(gp);
            result.success = id > 0;
            result.message = result.success ? "Gate pass request submitted. Status: PENDING." : "Submission failed.";
            result.passCode = gp.getPassCode();
        } catch (IllegalArgumentException e) {
            result.success = false;
            result.message = "Invalid date/time format.";
        } catch (SQLException e) {
            result.success = false;
            result.message = "Database error: " + e.getMessage();
        }
        return result;
    }

    public List<GatePass> getRequestsForStudent(int studentId) throws SQLException {
        return gatePassDAO.getByStudent(studentId);
    }

    public GatePass getById(int passId) throws SQLException {
        return gatePassDAO.getById(passId);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
