<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.niet.gatepass.dao.HostelDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
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
    <div class="card form-box">
        <h2>Login</h2>

        <% String err = (String) request.getAttribute("error");
           if (err == null) err = request.getParameter("error"); %>
        <% if (err != null) { %>
            <div class="alert alert-error">
                <% if ("session_expired".equals(err)) { %>Your session expired. Please login again.
                <% } else if ("access_denied".equals(err)) { %>You are not authorized to view that page.
                <% } else { %><%= err %><% } %>
            </div>
        <% } %>

        <form method="post" action="login" id="loginForm">
            <label>Login As</label>
            <select name="role" id="role" onchange="toggleGuardField()">
                <option value="STUDENT">Student</option>
                <option value="WARDEN">Warden</option>
                <option value="CHIEF_WARDEN">Chief Warden</option>
                <option value="GUARD">Security Guard</option>
            </select>

            <div id="emailPasswordFields">
                <label>Email</label>
                <input type="email" name="email" required>

                <label>Password</label>
                <input type="password" name="password" id="passwordField">
            </div>

            <div id="guardField" style="display:none;">
                <label>Guard Access Code</label>
                <input type="text" name="guardCode" placeholder="Provided by hostel administration">
            </div>

            <button class="btn" type="submit" style="margin-top:16px;width:100%;">Login</button>
        </form>

        <p style="margin-top:14px;font-size:14px;">
            New student? <a href="register.jsp">Register here</a>
        </p>
    </div>
</div>
<footer>&copy; 2026 NIET Hostel Smart Gate Pass System</footer>

<script>
function toggleGuardField() {
    var role = document.getElementById('role').value;
    var guard = document.getElementById('guardField');
    var normal = document.getElementById('emailPasswordFields');
    var pwd = document.getElementById('passwordField');
    if (role === 'GUARD') {
        guard.style.display = 'block';
        normal.style.display = 'none';
        pwd.removeAttribute('required');
    } else {
        guard.style.display = 'none';
        normal.style.display = 'block';
        pwd.setAttribute('required', 'required');
    }
}
</script>
</body>
</html>
