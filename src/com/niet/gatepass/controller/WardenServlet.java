package com.niet.gatepass.controller;

import com.niet.gatepass.dao.StudentDAO;
import com.niet.gatepass.model.GatePass;
import com.niet.gatepass.model.Student;
import com.niet.gatepass.model.Warden;
import com.niet.gatepass.service.ApprovalService;
import com.niet.gatepass.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Warden dashboard: view pending student registrations, view/approve/reject
 * gate pass requests for their hostel.
 * Mapped to /warden/dashboard, /warden/decide, /warden/student-decide
 */
@WebServlet({"/warden/dashboard", "/warden/decide", "/warden/student-decide"})
public class WardenServlet extends HttpServlet {

    private final ApprovalService approvalService = new ApprovalService();
    private final StudentDAO studentDAO = new StudentDAO();
    private final StudentService studentService = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Warden warden = (Warden) session.getAttribute("user");
        String tab = req.getParameter("tab"); // pending / approved / rejected

        try {
            List<GatePass> requests;
            if (tab == null || tab.equals("pending")) {
                requests = approvalService.getPendingForWarden(warden.getHostelId());
            } else if (tab.equals("approved")) {
                requests = approvalService.getForWardenByStatus(warden.getHostelId(), "WARDEN_APPROVED");
            } else {
                requests = approvalService.getForWardenByStatus(warden.getHostelId(), "WARDEN_REJECTED");
            }

            List<Student> pendingStudents = studentDAO.getPendingStudentsByHostel(warden.getHostelId());

            req.setAttribute("requests", requests);
            req.setAttribute("pendingStudents", pendingStudents);
            req.setAttribute("activeTab", tab == null ? "pending" : tab);
            req.getRequestDispatcher("/warden/dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/warden/dashboard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getServletPath();
        try {
            if (path.equals("/warden/student-decide")) {
                int studentId = Integer.parseInt(req.getParameter("studentId"));
                boolean approve = "approve".equals(req.getParameter("decision"));
                studentService.decideRegistration(studentId, approve);
            } else {
                int passId = Integer.parseInt(req.getParameter("passId"));
                boolean approve = "approve".equals(req.getParameter("decision"));
                String remarks = req.getParameter("remarks");
                approvalService.wardenDecision(passId, approve, remarks);
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/warden/dashboard");
    }
}
