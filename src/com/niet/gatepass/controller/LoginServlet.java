package com.niet.gatepass.controller;

import com.niet.gatepass.service.LoginService;
import com.niet.gatepass.service.LoginService.LoginResult;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final LoginService loginService = new LoginService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role"); // STUDENT / WARDEN / CHIEF_WARDEN / GUARD

        try {
            LoginResult result;
            if ("GUARD".equals(role)) {
                // Security guard has no DB account in this build - a shared access code is enough.
                String guardCode = req.getParameter("guardCode");
                if ("NIET-GATE-2026".equals(guardCode)) {
                    result = LoginResult.ok("GUARD", email);
                } else {
                    result = LoginResult.fail("Invalid guard access code.");
                }
            } else if ("WARDEN".equals(role)) {
                result = loginService.authenticateWarden(email, password);
            } else if ("CHIEF_WARDEN".equals(role)) {
                result = loginService.authenticateChiefWarden(email, password);
            } else {
                result = loginService.authenticateStudent(email, password);
            }

            if (!result.success) {
                req.setAttribute("error", result.message);
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("userRole", result.role);
            session.setAttribute("user", result.user);
            session.setAttribute("userEmail", email);

            switch (result.role) {
                case "STUDENT":
                    resp.sendRedirect(req.getContextPath() + "/student/dashboard");
                    break;
                case "WARDEN":
                    resp.sendRedirect(req.getContextPath() + "/warden/dashboard");
                    break;
                case "CHIEF_WARDEN":
                    resp.sendRedirect(req.getContextPath() + "/chiefwarden/dashboard");
                    break;
                case "GUARD":
                    resp.sendRedirect(req.getContextPath() + "/guard/verify");
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/login.jsp");
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
