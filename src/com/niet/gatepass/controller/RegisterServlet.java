package com.niet.gatepass.controller;

import com.niet.gatepass.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private final StudentService studentService = new StudentService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String rollNo = req.getParameter("rollNo");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        int hostelId = Integer.parseInt(req.getParameter("hostelId"));
        String roomNo = req.getParameter("roomNo");
        String contactNo = req.getParameter("contactNo");
        String emergencyContact = req.getParameter("emergencyContact");

        try {
            StudentService.RegistrationResult result = studentService.register(
                    name, rollNo, email, password, hostelId, roomNo, contactNo, emergencyContact);

            req.setAttribute("message", result.message);
            req.setAttribute("success", result.success);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        } catch (SQLException e) {
            req.setAttribute("message", "Database error: " + e.getMessage());
            req.setAttribute("success", false);
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }
}
