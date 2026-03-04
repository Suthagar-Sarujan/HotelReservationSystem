<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Help</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<%
    String role = String.valueOf(session.getAttribute("role"));
    boolean canAccessReports = "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
    boolean canManageReservations = "ADMIN".equalsIgnoreCase(role) || "RECEPTION".equalsIgnoreCase(role);
%>
<div class="app-shell">
    <header class="topbar no-print">
        <div class="brand">Support</div>
        <nav class="nav-links">
            <a class="btn btn-link" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/reservation">Reservations</a>
            <% if (canManageReservations) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/bill">Billing</a>
            <% } %>
            <% if (canAccessReports) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/report">Reports</a>
            <% } %>
            <a class="btn btn-link active" href="<%=request.getContextPath()%>/help">Help</a>
            <a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Logout</a>
        </nav>
    </header>

    <section class="page-header">
        <h1 class="page-title">Help and Usage Guide</h1>
        <p class="page-subtitle">Follow these steps for smooth operation during demos and viva.</p>
    </section>

    <section class="card">
        <h2 class="section-title">Core Workflow</h2>
        <ol class="help-list">
            <li>Login using provided credentials.</li>
            <li>Reception role: create, view, update, and delete reservations.</li>
            <li>Reception role: generate bills from the Calculate Bill page by reservation ID.</li>
            <li>Manager/Admin role: review occupancy and revenue trends from the Reports page.</li>
            <li>Use logout after completing operations to end the session safely.</li>
        </ol>
    </section>

    <section class="card">
        <h2 class="section-title">Quality Checklist</h2>
        <div class="stack">
            <p>1. Keep guest details complete and accurate before saving.</p>
            <p>2. Verify check-in/check-out dates and number of guests.</p>
            <p>3. Confirm totals and print bill only after reviewing values.</p>
            <p>4. Use search before deleting to avoid removing wrong records.</p>
        </div>
    </section>

    <section class="card">
        <h2 class="section-title">Role Access Matrix</h2>
        <div class="table-shell">
            <table>
                <thead>
                <tr>
                    <th>Page / Feature</th>
                    <th>Reception</th>
                    <th>Manager</th>
                    <th>Admin</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Dashboard</td>
                    <td>Yes</td>
                    <td>Yes</td>
                    <td>Yes</td>
                </tr>
                <tr>
                    <td>Add / Update / Delete Reservation</td>
                    <td>Yes</td>
                    <td>No</td>
                    <td>Yes</td>
                </tr>
                <tr>
                    <td>Calculate &amp; Print Bill</td>
                    <td>Yes</td>
                    <td>No</td>
                    <td>Yes</td>
                </tr>
                <tr>
                    <td>View Reservations</td>
                    <td>Yes</td>
                    <td>Yes (Read-only)</td>
                    <td>Yes</td>
                </tr>
                <tr>
                    <td>Reports + Export</td>
                    <td>No</td>
                    <td>Yes</td>
                    <td>Yes</td>
                </tr>
                </tbody>
            </table>
        </div>
    </section>
</div>
</body>
</html>
