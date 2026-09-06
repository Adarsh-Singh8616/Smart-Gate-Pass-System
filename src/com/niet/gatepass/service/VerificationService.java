package com.niet.gatepass.service;

import com.niet.gatepass.dao.GatePassDAO;
import com.niet.gatepass.dao.GatePassLogDAO;
import com.niet.gatepass.model.GatePass;

import java.sql.SQLException;

/** Used at the hostel gate by the Security Guard to verify and act on a gate pass. */
public class VerificationService {

    private final GatePassDAO gatePassDAO = new GatePassDAO();
    private final GatePassLogDAO logDAO = new GatePassLogDAO();

    public static class VerifyResult {
        public boolean valid;
        public String message;
        public GatePass gatePass;
    }

    /** Looks up a pass by its code and reports whether it is currently valid for action. */
    public VerifyResult verify(String passCode) throws SQLException {
        VerifyResult result = new VerifyResult();
        GatePass gp = gatePassDAO.getByPassCode(passCode);
        if (gp == null) {
            result.valid = false;
            result.message = "No gate pass found with this code.";
            return result;
        }
        result.gatePass = gp;
        switch (gp.getStatus()) {
            case "ACTIVE":
                result.valid = true;
                result.message = "Pass approved and active. Ready for EXIT.";
                break;
            case "EXIT_RECORDED":
                result.valid = true;
                result.message = "Student is currently out. Ready for RETURN.";
                break;
            case "RETURN_RECORDED":
            case "COMPLETED":
                result.valid = false;
                result.message = "This gate pass has already been completed.";
                break;
            default:
                result.valid = false;
                result.message = "Pass is not approved (current status: " + gp.getStatus() + ").";
        }
        return result;
    }

    public boolean recordExit(int passId, String guardName) throws SQLException {
        logDAO.addLog(passId, "EXIT", guardName, "Student exited hostel gate");
        return gatePassDAO.updateStatus(passId, "EXIT_RECORDED");
    }

    public boolean recordReturn(int passId, String guardName) throws SQLException {
        logDAO.addLog(passId, "RETURN", guardName, "Student returned to hostel gate");
        return gatePassDAO.updateStatus(passId, "COMPLETED");
    }
}
