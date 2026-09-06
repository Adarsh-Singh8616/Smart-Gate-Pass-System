package com.niet.gatepass.controller;

import com.niet.gatepass.model.GatePass;
import com.niet.gatepass.service.ApprovalService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Chief Warden dashboard: final approval on requests already approved by a warden.
 * Mapped to /chiefwarden/dashboard, /chiefwarden/decide
 */
@WebServlet({"/chiefwarden/dashboard", "/chiefwarden/decide"})
public class ChiefWardenServlet extends HttpServlet {

    private final ApprovalService approvalService = new ApprovalService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String tab = req.getParameter("tab"); // pending / active / rejected

        try {
            List<GatePass> requests;
            if (tab == null || tab.equals("pending")) {
                requests = approvalService.getPendingForChiefWarden();
            } else if (tab.equals("active")) {
                requests = approvalService.getForChiefWardenByStatus("ACTIVE");
            } else {
                requests = approvalService.getForChiefWardenByStatus("CHIEF_WARDEN_REJECTED");
            }
            req.setAttribute("requests", requests);
            req.setAttribute("activeTab", tab == null ? "pending" : tab);
            req.getRequestDispatcher("/chiefwarden/dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/chiefwarden/dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int passId = Integer.parseInt(req.getParameter("passId"));
        boolean approve = "approve".equals(req.getParameter("decision"));
        String remarks = req.getParameter("remarks");
        try {
            approvalService.chiefWardenDecision(passId, approve, remarks);
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/chiefwarden/dashboard");
    }
}
