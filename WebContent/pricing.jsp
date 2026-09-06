<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.dao.HostelDAO" %>
<%@ page import="com.niet.gatepass.model.Hostel" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pricing</title>
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
    <h1>Fee Structure</h1>
    <table>
        <tr><th>Hostel</th><th>Rooms</th><th>Hostel Fee</th><th>Mess Fee</th></tr>
        <%
            List<Hostel> hostels = new HostelDAO().getAllHostels();
            for (Hostel h : hostels) {
        %>
        <tr>
            <td><%= h.getHostelName() %></td>
            <td><%= h.getTotalRooms() %></td>
            <td>&#8377;<%= h.getHostelFee() %></td>
            <td>&#8377;<%= h.getMessFee() %></td>
        </tr>
        <% } %>
    </table>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
