<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.model.GatePass" %>
<%@ page import="com.niet.gatepass.model.Student" %>
<%@ page import="com.niet.gatepass.model.Warden" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Warden Dashboard</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header class="navbar">
    <div class="brand">NIET Hostel Gate Pass - Warden</div>
    <nav>
        <a href="dashboard">Dashboard</a>
        <a href="../logout">Logout</a>
    </nav>
</header>
<div class="container">
    <% Warden warden = (Warden) session.getAttribute("user"); %>
    <h1>Welcome, <%= warden.getName() %></h1>

    <div class="card">
        <h3>Pending Student Registrations</h3>
        <%
            List<Student> pendingStudents = (List<Student>) request.getAttribute("pendingStudents");
        %>
        <% if (pendingStudents == null || pendingStudents.isEmpty()) { %>
            <p>No pending student registrations.</p>
        <% } else { %>
        <table>
            <tr><th>Name</th><th>Roll No</th><th>Email</th><th>Room</th><th>Action</th></tr>
            <% for (Student s : pendingStudents) { %>
            <tr>
                <td><%= s.getName() %></td>
                <td><%= s.getRollNo() %></td>
                <td><%= s.getEmail() %></td>
                <td><%= s.getRoomNo() %></td>
                <td>
                    <form method="post" action="student-decide" style="display:inline;">
                        <input type="hidden" name="studentId" value="<%= s.getStudentId() %>">
                        <input type="hidden" name="decision" value="approve">
                        <button class="btn btn-approve" type="submit">Approve</button>
                    </form>
                    <form method="post" action="student-decide" style="display:inline;">
                        <input type="hidden" name="studentId" value="<%= s.getStudentId() %>">
                        <input type="hidden" name="decision" value="reject">
                        <button class="btn btn-reject" type="submit">Reject</button>
                    </form>
                </td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>

    <h3>Gate Pass Requests</h3>
    <%
        String activeTab = (String) request.getAttribute("activeTab");
        if (activeTab == null) activeTab = "pending";
    %>
    <div class="tabs">
        <a href="dashboard?tab=pending"  class="<%= activeTab.equals("pending")  ? "active" : "" %>">Pending</a>
        <a href="dashboard?tab=approved" class="<%= activeTab.equals("approved") ? "active" : "" %>">Approved (sent to Chief Warden)</a>
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
            <tr><th>Student</th><th>Destination</th><th>Out Date</th><th>Return By</th><th>Status</th>
                <% if (activeTab.equals("pending")) { %><th>Action</th><% } %>
            </tr>
            <% for (GatePass gp : requests) { %>
            <tr>
                <td><%= gp.getStudentName() %> (<%= gp.getRollNo() %>, Rm <%= gp.getRoomNo() %>)</td>
                <td><%= gp.getDestination() %></td>
                <td><%= gp.getOutDate() %> <%= gp.getOutTime() %></td>
                <td><%= gp.getExpectedReturn() %></td>
                <td><span class="badge badge-<%= gp.getStatus().toLowerCase().contains("reject") ? "rejected" : "progress" %>"><%= gp.getStatus() %></span></td>
                <% if (activeTab.equals("pending")) { %>
                <td>
                    <form method="post" action="decide">
                        <input type="hidden" name="passId" value="<%= gp.getPassId() %>">
                        <input type="text" name="remarks" placeholder="Remarks (optional)" style="width:140px;display:inline-block;">
                        <button class="btn btn-approve" type="submit" name="decision" value="approve">Approve</button>
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
