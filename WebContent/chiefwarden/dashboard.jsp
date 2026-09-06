<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.model.GatePass" %>
<%@ page import="com.niet.gatepass.model.ChiefWarden" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chief Warden Dashboard</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header class="navbar">
    <div class="brand">NIET Hostel Gate Pass - Chief Warden</div>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="../logout">Logout</a>
    </nav>
</header>
<div class="container">
    <% ChiefWarden cw = (ChiefWarden) session.getAttribute("user"); %>
    <h1>Welcome, <%= cw.getName() %></h1>

    <%
        String activeTab = (String) request.getAttribute("activeTab");
        if (activeTab == null) activeTab = "pending";
    %>
    <div class="tabs">
        <a href="dashboard?tab=pending"  class="<%= activeTab.equals("pending")  ? "active" : "" %>">Awaiting Final Approval</a>
        <a href="dashboard?tab=active"   class="<%= activeTab.equals("active")   ? "active" : "" %>">Approved / Active</a>
        <a href="dashboard?tab=rejected" class="<%= activeTab.equals("rejected") ? "active" : "" %>">Rejected</a>
    </div>

    <div class="card">
        <%
            List<GatePass> requests = (List<GatePass>) request.getAttribute("requests");
        %>
        <% if (requests == null || requests.isEmpty()) { %>
            <p>No requests in this category.</p>
        <% } else { %>
        <table>
            <tr><th>Student</th><th>Hostel</th><th>Destination</th><th>Out Date</th><th>Warden Remarks</th><th>Status</th>
                <% if (activeTab.equals("pending")) { %><th>Action</th><% } %>
            </tr>
            <% for (GatePass gp : requests) { %>
            <tr>
                <td><%= gp.getStudentName() %> (<%= gp.getRollNo() %>)</td>
                <td><%= gp.getHostelName() %></td>
                <td><%= gp.getDestination() %></td>
                <td><%= gp.getOutDate() %> <%= gp.getOutTime() %></td>
                <td><%= gp.getWardenRemarks() != null ? gp.getWardenRemarks() : "-" %></td>
                <td><span class="badge badge-<%= gp.getStatus().toLowerCase().contains("reject") ? "rejected" : "progress" %>"><%= gp.getStatus() %></span></td>
                <% if (activeTab.equals("pending")) { %>
                <td>
                    <form method="post" action="decide">
                        <input type="hidden" name="passId" value="<%= gp.getPassId() %>">
                        <input type="text" name="remarks" placeholder="Remarks (optional)" style="width:140px;display:inline-block;">
                        <button class="btn btn-approve" type="submit" name="decision" value="approve">Final Approve</button>
                        <button class="btn btn-reject" type="submit" name="decision" value="reject">Reject</button>
                    </form>
                </td>
                <% } %>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
