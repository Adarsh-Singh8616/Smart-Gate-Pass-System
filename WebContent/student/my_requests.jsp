<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.model.GatePass" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>My Requests</title>
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
    <h1>My Gate Pass Requests</h1>
    <div class="card">
        <%
            List<GatePass> requests = (List<GatePass>) request.getAttribute("requests");
        %>
        <% if (requests == null || requests.isEmpty()) { %>
            <p>No requests found. <a href="apply">Apply for one now</a>.</p>
        <% } else { %>
        <table>
            <tr><th>Pass Code</th><th>Destination</th><th>Out Date/Time</th><th>Expected Return</th><th>Status</th><th>Remarks</th></tr>
            <% for (GatePass gp : requests) { %>
            <tr>
                <td><%= gp.getPassCode() %></td>
                <td><%= gp.getDestination() %></td>
                <td><%= gp.getOutDate() %> <%= gp.getOutTime() %></td>
                <td><%= gp.getExpectedReturn() %></td>
                <td><span class="badge badge-<%= gp.getStatus().toLowerCase().contains("reject") ? "rejected" : gp.getStatus().equalsIgnoreCase("pending") ? "pending" : "progress" %>"><%= gp.getStatus() %></span></td>
                <td>
                    <% if (gp.getWardenRemarks() != null) { %>Warden: <%= gp.getWardenRemarks() %><br><% } %>
                    <% if (gp.getChiefWardenRemarks() != null) { %>Chief Warden: <%= gp.getChiefWardenRemarks() %><% } %>
                </td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
