<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.dao.HostelDAO" %>
<%@ page import="com.niet.gatepass.model.Hostel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>NIET Hostel - Smart Gate Pass System</title>
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

<section class="hero">
    <h1>Welcome to NIET Hostel</h1>
    <p>Apply, track, and verify gate passes online - no more paper registers.</p>
</section>

<div class="container">
    <div class="card">
        <h2>About NIET Hostel</h2>
        <p>NIET Hostel provides safe, well-maintained accommodation for students with round-the-clock
           security and a fully digital gate pass approval workflow involving your Warden and Chief Warden.</p>
        <p><strong>Facilities:</strong> Wi-Fi &middot; Mess &middot; Security &middot; CCTV &middot; Sports &middot; Medical Facility</p>
    </div>

    <h2>Hostels</h2>
    <div class="grid-3">
        <%
            List<Hostel> hostels = new HostelDAO().getAllHostels();
            for (Hostel h : hostels) {
        %>
        <div class="card">
            <h3><%= h.getHostelName() %></h3>
            <p>Rooms: <%= h.getTotalRooms() %></p>
            <p>Hostel Fee: &#8377;<%= h.getHostelFee() %></p>
            <p>Mess Fee: &#8377;<%= h.getMessFee() %></p>
            <p><%= h.getFacilities() %></p>
        </div>
        <% } %>
    </div>

    <div class="card" style="text-align:center;">
        <a class="btn" href="login.jsp">Login / Apply Gate Pass</a>
        <a class="btn btn-outline" href="pricing.jsp">View Pricing</a>
    </div>
</div>

<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
