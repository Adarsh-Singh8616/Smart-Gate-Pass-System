<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.dao.HostelDAO" %>
<%@ page import="com.niet.gatepass.model.Hostel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Registration</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<header class="navbar">
    <div class="brand">NIET Hostel Gate Pass</div>
    <nav>
        <a href="index.jsp">Home</a>
        <a href="hostel.jsp">Hostel</a>
        <a href="pricing.jsp">Pricing</a>
        <a href="login.jsp">Login</a>
    </nav>
</header>
<div class="container">
    <div class="card form-box">
        <h2>Student Registration</h2>

        <% Boolean success = (Boolean) request.getAttribute("success");
           String message = (String) request.getAttribute("message");
           if (message != null) { %>
            <div class="alert <%= Boolean.TRUE.equals(success) ? "alert-success" : "alert-error" %>">
                <%= message %>
            </div>
        <% } %>

        <form method="post" action="register">
            <label>Full Name</label>
            <input type="text" name="name" required>

            <label>Student / Roll No.</label>
            <input type="text" name="rollNo" required>

            <label>Email</label>
            <input type="email" name="email" required>

            <label>Password</label>
            <input type="password" name="password" minlength="6" required>

            <label>Hostel</label>
            <select name="hostelId" required>
                <%
                    List<Hostel> hostels = new HostelDAO().getAllHostels();
                    for (Hostel h : hostels) {
                %>
                <option value="<%= h.getHostelId() %>"><%= h.getHostelName() %></option>
                <% } %>
            </select>

            <label>Room No.</label>
            <input type="text" name="roomNo" required>

            <label>Contact No.</label>
            <input type="text" name="contactNo" required>

            <label>Emergency Contact</label>
            <input type="text" name="emergencyContact" required>

            <button class="btn" type="submit" style="margin-top:16px;width:100%;">Register</button>
        </form>

        <p style="margin-top:14px;font-size:14px;">
            Already registered? <a href="login.jsp">Login here</a>
        </p>
        <p style="font-size:13px;color:#666;">
            Note: your account will show as PENDING until your Warden approves it.
        </p>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
