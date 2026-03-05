<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login | Hotel Reservation System</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body class="page-login">
<div class="app-shell">
    <div class="login-layout">
        <section class="login-intro">
            <div class="stack">
                <p class="eyebrow">Hospitality Operations</p>
                <h1>Hotel Reservation System</h1>
                <p>Manage guest reservations, billing, and reporting from one professional operations workspace.</p>
            </div>
            <ul class="login-points">
                <li>Fast guest check-in and reservation capture</li>
                <li>Accurate billing with print-ready statements</li>
                <li>Role-based access for reception, manager, and admin</li>
            </ul>
        </section>

        <section class="card login-card">
            <p class="eyebrow">Secure Access</p>
            <h2 class="section-title">Sign in to continue</h2>
            <p class="section-subtitle">Use your account credentials to access dashboard tools.</p>

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
</div>
<script src="<%=request.getContextPath()%>/js/validation.js"></script>
</body>
</html>
