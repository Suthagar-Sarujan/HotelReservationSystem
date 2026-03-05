<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.hotel.model.Reservation" %>
<%
    Map<String, Object> stats = (Map<String, Object>) request.getAttribute("stats");
    List<Reservation> recent = stats == null ? null : (List<Reservation>) stats.get("recentReservations");
    Object totalReservations = stats != null ? stats.get("totalReservations") : 0;
    Object totalRevenue = stats != null ? stats.get("totalRevenue") : 0;
    Object occupancyRate = stats != null ? stats.get("occupancyRate") : 0;
    String role = String.valueOf(session.getAttribute("role"));
    boolean canAccessReports = "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
    boolean canManageReservations = "ADMIN".equalsIgnoreCase(role) || "RECEPTION".equalsIgnoreCase(role);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<div class="app-shell">
    <header class="topbar no-print">
        <div class="topbar-main">
            <div class="brand-block">
                <span class="eyebrow">Operations Suite</span>
                <div class="brand">Harbor View Hotel</div>
                <div class="brand-sub">Reservation management console</div>
            </div>
            <div class="brand-meta">${sessionScope.username} | <%= role %></div>
        </div>
        <nav class="nav-links">
            <a class="btn btn-link active" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
            <% if (canManageReservations) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/reservation/add">New Reservation</a>
            <% } %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/reservation">View Reservations</a>
            <% if (canManageReservations) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/bill">Calculate Bill</a>
            <% } %>
            <% if (canAccessReports) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/report">Reports</a>
            <% } %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/help">Help</a>
            <a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Logout</a>
        </nav>
    </header>

    <section class="page-header">
        <h1 class="page-title">Operations Dashboard</h1>
        <p class="page-subtitle">Welcome, <strong>${sessionScope.username}</strong>. Track reservation activity, room occupancy, and revenue performance from one place.</p>
    </section>

    <% if (session.getAttribute("message") != null) { %>
    <div class="alert alert-success"><%= session.getAttribute("message") %></div>
    <% session.removeAttribute("message"); %>
    <% } %>

    <% if (session.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= session.getAttribute("error") %></div>
    <% session.removeAttribute("error"); %>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <section class="card no-print">
        <h2 class="section-title">Quick Actions</h2>
        <p class="section-subtitle">Start a task directly from your role-approved workflows.</p>
        <div class="quick-grid">
            <% if (canManageReservations) { %>
            <a class="quick-item" href="<%=request.getContextPath()%>/reservation/add">
                <strong>Create Reservation</strong>
                <span>Capture a new guest booking.</span>
            </a>
            <% } %>
            <a class="quick-item" href="<%=request.getContextPath()%>/reservation">
                <strong>View Reservations</strong>
                <span>Search, inspect, and manage records.</span>
            </a>
            <% if (canManageReservations) { %>
            <a class="quick-item" href="<%=request.getContextPath()%>/bill">
                <strong>Generate Bill</strong>
                <span>Produce and print payment details.</span>
            </a>
            <% } %>
            <% if (canAccessReports) { %>
            <a class="quick-item" href="<%=request.getContextPath()%>/report">
                <strong>Open Reports</strong>
                <span>Review occupancy and revenue trends.</span>
            </a>
            <% } %>
            <a class="quick-item" href="<%=request.getContextPath()%>/help">
                <strong>Help Center</strong>
                <span>Check role guide and process notes.</span>
            </a>
        </div>
    </section>

    <section class="card">
        <h2 class="section-title">Key Metrics</h2>
        <p class="section-subtitle">Current operational snapshot generated from reservation data.</p>
        <div class="metric-grid">
            <article class="metric-card">
                <span class="label">Total Reservations</span>
                <span class="value"><%= totalReservations %></span>
            </article>
            <article class="metric-card">
                <span class="label">Total Revenue</span>
                <span class="value">$<%= totalRevenue %></span>
            </article>
            <article class="metric-card">
                <span class="label">Occupancy Rate</span>
                <span class="value"><%= occupancyRate %>%</span>
            </article>
        </div>
    </section>

    <section class="card">
        <h2 class="section-title">Recent Reservations</h2>
        <p class="section-subtitle">Latest bookings to review and process quickly.</p>
        <div class="table-shell">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Reservation No</th>
                    <th>Guest</th>
                    <th>Room</th>
                    <th>Total</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (recent != null && !recent.isEmpty()) {
                        for (Reservation r : recent) {
                %>
                <tr>
                    <td><%= r.getId() %></td>
                    <td><%= r.getReservationNo() %></td>
                    <td><%= r.getGuestName() %></td>
                    <td><%= r.getRoomType() %></td>
                    <td>$<%= r.getTotalAmount() %></td>
                    <td class="actions">
                        <a class="btn btn-secondary btn-sm" href="<%=request.getContextPath()%>/reservation/view?id=<%=r.getId()%>">View</a>
                        <% if (canManageReservations) { %>
                        <a class="btn btn-secondary btn-sm" href="<%=request.getContextPath()%>/bill?reservationId=<%=r.getId()%>">Bill</a>
                        <% } %>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="empty-state">No reservations yet. Click "New Reservation" to create the first booking.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</div>
</body>
</html>
