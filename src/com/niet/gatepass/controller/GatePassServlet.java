package com.niet.gatepass.controller;

import com.niet.gatepass.model.Student;
import com.niet.gatepass.service.GatePassService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/** Handles the "Apply Gate Pass" form: GET shows it, POST submits the request. */
@WebServlet("/student/apply")
public class GatePassServlet extends HttpServlet {

    private final GatePassService gatePassService = new GatePassService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/student/apply_gatepass.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Student student = (Student) session.getAttribute("user");

        String destination = req.getParameter("destination");
        String reason = req.getParameter("reason");
        String outDate = req.getParameter("outDate");
        String outTime = req.getParameter("outTime");
        String expectedReturn = req.getParameter("expectedReturn");
        String emergencyContact = req.getParameter("emergencyContact");

        GatePassService.ApplyResult result = gatePassService.apply(
                student.getStudentId(), destination, reason, outDate, outTime, expectedReturn, emergencyContact);

        req.setAttribute("message", result.message);
        req.setAttribute("success", result.success);
        req.setAttribute("passCode", result.passCode);
        req.getRequestDispatcher("/student/apply_gatepass.jsp").forward(req, resp);
    }
}
