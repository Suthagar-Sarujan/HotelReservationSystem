<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body class="page-login">
<div class="app-shell">
    <section class="card login-card">
        <p class="eyebrow">CIS 6003 Project</p>
        <h1 class="page-title">Hotel Reservation System</h1>
        <p class="page-subtitle">Sign in to manage reservations, bills, and reports.</p>

        <% if (request.getAttribute("error") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
        <% } %>

        <form class="form-grid single-column" action="<%=request.getContextPath()%>/login" method="post" onsubmit="return validateLoginForm()">
            <div class="field">
                <label for="username">Username</label>
                <input id="username" type="text" name="username" placeholder="Enter your username" required autocomplete="username"/>
            </div>
            <div class="field">
                <label for="password">Password</label>
                <input id="password" type="password" name="password" placeholder="Enter your password" required autocomplete="current-password"/>
            </div>
            <button class="btn btn-primary btn-block" type="submit">Login</button>
        </form>

        <p class="hint">Demo users: <strong>admin / admin123</strong>, <strong>manager / admin123</strong>, and <strong>reception / admin123</strong></p>
    </section>
</div>
<script src="<%=request.getContextPath()%>/js/validation.js"></script>
</body>
</html>
