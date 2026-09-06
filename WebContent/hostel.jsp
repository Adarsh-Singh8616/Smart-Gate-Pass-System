<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.dao.HostelDAO" %>
<%@ page import="com.niet.gatepass.model.Hostel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hostel Information</title>
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
    <h1>Hostel Information &amp; Rules</h1>
    <div class="card">
        <h3>General Rules</h3>
        <ul>
            <li>Every student must register once and be approved by their Warden before logging in.</li>
            <li>Every outing requires an approved Gate Pass - Warden approval followed by Chief Warden's final approval.</li>
            <li>Show your Pass ID / QR Code to the Security Guard at the gate for entry and exit.</li>
            <li>Return within the "Expected Return" time mentioned on your pass.</li>
        </ul>
    </div>
    <div class="grid-3">
        <%
            List<Hostel> hostels = new HostelDAO().getAllHostels();
            for (Hostel h : hostels) {
        %>
        <div class="card">
            <h3><%= h.getHostelName() %></h3>
            <p>Total Rooms: <%= h.getTotalRooms() %></p>
            <p>Facilities: <%= h.getFacilities() %></p>
        </div>
        <% } %>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
