<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.service.VerificationService.VerifyResult" %>
<%@ page import="com.niet.gatepass.model.GatePass" %>
<!DOCTYPE html>
<html>
<head>
    <title>Verify Gate Pass</title>
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<header class="navbar">
    <div class="brand">NIET Hostel Gate Pass - Security Guard</div>
    <nav>
        <a href="verify">Verify Pass</a>
        <a href="../logout">Logout</a>
    </nav>
</header>
<div class="container">
    <div class="card form-box">
        <h2>Verify Gate Pass</h2>
        <form method="get" action="verify">
            <label>Enter Pass ID / Scan QR Code</label>
            <input type="text" name="passCode" placeholder="e.g. GP-3F2A9C11" required>
            <button class="btn" type="submit" style="margin-top:12px;width:100%;">Verify</button>
        </form>
    </div>

    <%
        String error = (String) request.getAttribute("error");
        VerifyResult result = (VerifyResult) request.getAttribute("result");
    %>
    <% if (error != null) { %>
        <div class="alert alert-error"><%= error %></div>
    <% } %>

    <% if (result != null) { %>
        <div class="card">
            <% if (result.gatePass == null) { %>
                <div class="alert alert-error"><%= result.message %></div>
            <% } else {
                GatePass gp = result.gatePass;
            %>
                <div class="alert <%= result.valid ? "alert-success" : "alert-error" %>">
                    <%= result.valid ? "VALID" : "INVALID" %> - <%= result.message %>
                </div>
                <p><strong>Student:</strong> <%= gp.getStudentName() %> (<%= gp.getRollNo() %>)</p>
                <p><strong>Hostel / Room:</strong> <%= gp.getHostelName() %> / <%= gp.getRoomNo() %></p>
                <p><strong>Destination:</strong> <%= gp.getDestination() %></p>
                <p><strong>Expected Return:</strong> <%= gp.getExpectedReturn() %></p>
                <p><strong>Pass Status:</strong> <span class="badge badge-progress"><%= gp.getStatus() %></span></p>

                <% if (result.valid && "ACTIVE".equals(gp.getStatus())) { %>
                <form method="post" action="verify">
                    <input type="hidden" name="passId" value="<%= gp.getPassId() %>">
                    <input type="hidden" name="action" value="EXIT">
                    <button class="btn btn-approve" type="submit">Allow Exit</button>
                </form>
                <% } else if (result.valid && "EXIT_RECORDED".equals(gp.getStatus())) { %>
                <form method="post" action="verify">
                    <input type="hidden" name="passId" value="<%= gp.getPassId() %>">
                    <input type="hidden" name="action" value="RETURN">
                    <button class="btn btn-approve" type="submit">Record Return / Allow Entry</button>
                </form>
                <% } %>
            <% } %>
        </div>
    <% } %>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
