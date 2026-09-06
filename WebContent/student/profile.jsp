<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header class="navbar">
    <div class="brand">NIET Hostel Gate Pass</div>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="apply">Apply Gate Pass</a>
        <a href="requests">My Requests</a>
        <a href="profile">Profile</a>
        <a href="../logout">Logout</a>
    </nav>
</header>
<div class="container">
    <h1>My Profile</h1>
    <%
        Student s = (Student) request.getAttribute("student");
        if (s == null) s = (Student) session.getAttribute("user");
    %>
    <div class="card">
        <p><strong>Name:</strong> <%= s.getName() %></p>
        <p><strong>Roll No:</strong> <%= s.getRollNo() %></p>
        <p><strong>Email:</strong> <%= s.getEmail() %></p>
        <p><strong>Hostel:</strong> <%= s.getHostelName() %></p>
        <p><strong>Room No:</strong> <%= s.getRoomNo() %></p>
        <p><strong>Contact No:</strong> <%= s.getContactNo() %></p>
        <p><strong>Emergency Contact:</strong> <%= s.getEmergencyContact() %></p>
        <p><strong>Account Status:</strong> <span class="badge badge-approved"><%= s.getStatus() %></span></p>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
