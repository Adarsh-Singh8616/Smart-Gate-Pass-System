package com.niet.gatepass.controller;

import com.niet.gatepass.model.GatePass;
import com.niet.gatepass.model.Student;
import com.niet.gatepass.service.GatePassService;
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
 * Handles the student area: dashboard, viewing own requests, and profile.
 * Mapped to /student/dashboard, /student/requests, /student/profile
 */
@WebServlet({"/student/dashboard", "/student/requests", "/student/profile"})
public class StudentServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();
    private final GatePassService gatePassService = new GatePassService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Student student = (Student) session.getAttribute("user");
        String path = req.getServletPath();

        try {
            if (path.equals("/student/requests")) {
                List<GatePass> requests = gatePassService.getRequestsForStudent(student.getStudentId());
                req.setAttribute("requests", requests);
                req.getRequestDispatcher("/student/my_requests.jsp").forward(req, resp);
            } else if (path.equals("/student/profile")) {
                Student fresh = studentService.getProfile(student.getStudentId());
                req.setAttribute("student", fresh);
                req.getRequestDispatcher("/student/profile.jsp").forward(req, resp);
            } else {
                List<GatePass> requests = gatePassService.getRequestsForStudent(student.getStudentId());
                req.setAttribute("requests", requests);
                req.setAttribute("student", student);
                req.getRequestDispatcher("/student/dashboard.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("/student/dashboard.jsp").forward(req, resp);
        }
    }
}
