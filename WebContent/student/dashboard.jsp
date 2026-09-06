<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.model.GatePass" %>
<%@ page import="com.niet.gatepass.model.Student" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
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
    <% Student student = (Student) session.getAttribute("user"); %>
    <h1>Welcome, <%= student.getName() %></h1>
    <p><%= student.getRollNo() %> &middot; <%= student.getHostelName() %>, Room <%= student.getRoomNo() %></p>

    <div class="grid-3">
        <div class="card">
            <h3>Apply Gate Pass</h3>
            <p>Submit a new outing request for Warden &amp; Chief Warden approval.</p>
            <a class="btn" href="apply">Apply Now</a>
        </div>
        <div class="card">
            <h3>My Requests</h3>
            <p>Track the status of all your gate pass requests.</p>
            <a class="btn btn-outline" href="requests">View Requests</a>
        </div>
        <div class="card">
            <h3>Profile</h3>
            <p>View your registered hostel details.</p>
            <a class="btn btn-outline" href="profile">View Profile</a>
        </div>
    </div>

    <div class="card">
        <h3>Recent Requests</h3>
        <%
            List<GatePass> requests = (List<GatePass>) request.getAttribute("requests");
        %>
        <% if (requests == null || requests.isEmpty()) { %>
            <p>You have not applied for any gate pass yet.</p>
        <% } else { %>
        <table>
            <tr><th>Pass Code</th><th>Destination</th><th>Out Date</th><th>Status</th></tr>
            <% for (GatePass gp : requests) { %>
            <tr>
                <td><%= gp.getPassCode() %></td>
                <td><%= gp.getDestination() %></td>
                <td><%= gp.getOutDate() %></td>
                <td><span class="badge badge-<%= gp.getStatus().toLowerCase().contains("reject") ? "rejected" : gp.getStatus().equalsIgnoreCase("pending") ? "pending" : "progress" %>"><%= gp.getStatus() %></span></td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
