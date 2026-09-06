<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Apply Gate Pass</title>
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
    <div class="card form-box">
        <h2>Apply for Gate Pass</h2>

        <% Boolean success = (Boolean) request.getAttribute("success");
           String message = (String) request.getAttribute("message");
           String passCode = (String) request.getAttribute("passCode");
           if (message != null) { %>
            <div class="alert <%= Boolean.TRUE.equals(success) ? "alert-success" : "alert-error" %>">
                <%= message %>
                <% if (Boolean.TRUE.equals(success) && passCode != null) { %>
                    <br><br>Your Pass Code: <span class="pass-code"><%= passCode %></span>
                    <br><small>Keep this safe - you will need it (or its QR) at the gate once approved.</small>
                <% } %>
            </div>
        <% } %>

        <form method="post" action="apply">
            <label>Destination</label>
            <input type="text" name="destination" required>

            <label>Reason</label>
            <textarea name="reason" rows="3" required></textarea>

            <label>Out Date</label>
            <input type="date" name="outDate" required>

            <label>Out Time</label>
            <input type="time" name="outTime" required>

            <label>Expected Return (date &amp; time)</label>
            <input type="datetime-local" name="expectedReturn" required>

            <label>Emergency Contact</label>
            <input type="text" name="emergencyContact" required>

            <button class="btn" type="submit" style="margin-top:16px;width:100%;">Submit Request</button>
        </form>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>
</body>
</html>
