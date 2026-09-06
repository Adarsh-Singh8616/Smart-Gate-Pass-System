package com.niet.gatepass.controller;

import com.niet.gatepass.service.VerificationService;
import com.niet.gatepass.service.VerificationService.VerifyResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Security Guard's verification screen: enter a Pass ID / scanned QR code,
 * check validity, then record EXIT or RETURN.
 * Mapped to /guard/verify (GET: form + lookup, POST: record exit/return)
 */
@WebServlet("/guard/verify")
public class VerifyPassServlet extends HttpServlet {

    private final VerificationService verificationService = new VerificationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String passCode = req.getParameter("passCode");
        if (passCode != null && !passCode.trim().isEmpty()) {
            try {
                VerifyResult result = verificationService.verify(passCode.trim());
                req.setAttribute("result", result);
            } catch (SQLException e) {
                req.setAttribute("error", "Database error: " + e.getMessage());
            }
        }
        req.getRequestDispatcher("/guard/verify.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int passId = Integer.parseInt(req.getParameter("passId"));
        String action = req.getParameter("action"); // EXIT or RETURN
        HttpSession session = req.getSession(false);
        String guardName = (String) session.getAttribute("userEmail");

        try {
            if ("EXIT".equals(action)) {
                verificationService.recordExit(passId, guardName);
            } else if ("RETURN".equals(action)) {
                verificationService.recordReturn(passId, guardName);
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/guard/verify");
    }
}
