package com.niet.gatepass.service;

import com.niet.gatepass.dao.GatePassDAO;
import com.niet.gatepass.model.GatePass;

import java.sql.SQLException;
import java.util.List;

/**
 * Encapsulates the two-stage approval workflow:
 *  Warden decision  -> WARDEN_APPROVED / WARDEN_REJECTED
 *  Chief Warden decision (only on WARDEN_APPROVED requests) -> CHIEF_WARDEN_APPROVED / CHIEF_WARDEN_REJECTED
 * A CHIEF_WARDEN_APPROVED pass becomes ACTIVE and is ready for gate verification.
 */
public class ApprovalService {

    private final GatePassDAO gatePassDAO = new GatePassDAO();

    public List<GatePass> getPendingForWarden(int hostelId) throws SQLException {
        return gatePassDAO.getPendingForWarden(hostelId);
    }

    public List<GatePass> getForWardenByStatus(int hostelId, String status) throws SQLException {
        return gatePassDAO.getByHostelAndStatus(hostelId, status);
    }

    public boolean wardenDecision(int passId, boolean approve, String remarks) throws SQLException {
        String newStatus = approve ? "WARDEN_APPROVED" : "WARDEN_REJECTED";
        return gatePassDAO.updateStatus(passId, newStatus, "warden_remarks", remarks);
    }

    public List<GatePass> getPendingForChiefWarden() throws SQLException {
        return gatePassDAO.getPendingForChiefWarden();
    }

    public List<GatePass> getForChiefWardenByStatus(String status) throws SQLException {
        return gatePassDAO.getAllByStatus(status);
    }

    public boolean chiefWardenDecision(int passId, boolean approve, String remarks) throws SQLException {
        String newStatus = approve ? "ACTIVE" : "CHIEF_WARDEN_REJECTED";
        boolean ok = gatePassDAO.updateStatus(passId, newStatus, "chief_warden_remarks", remarks);
        return ok;
    }
}
