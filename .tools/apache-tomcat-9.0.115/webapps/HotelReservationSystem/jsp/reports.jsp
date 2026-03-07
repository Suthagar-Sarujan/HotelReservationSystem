<%@ page import="java.util.Map" %>
<%
    Map<String, Object> occupancy = (Map<String, Object>) request.getAttribute("occupancy");
    Map<String, Object> revenue = (Map<String, Object>) request.getAttribute("revenue");
    String role = String.valueOf(session.getAttribute("role"));
    boolean canAccessReports = "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
    boolean canManageReservations = "ADMIN".equalsIgnoreCase(role) || "RECEPTION".equalsIgnoreCase(role);
    double occupancyRate = 0.0;
    if (occupancy != null && occupancy.get("occupancyRate") instanceof Number) {
        occupancyRate = ((Number) occupancy.get("occupancyRate")).doubleValue();
    }
    if (occupancyRate < 0) {
        occupancyRate = 0.0;
    }
    if (occupancyRate > 100) {
        occupancyRate = 100.0;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reports</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<div class="app-shell">
    <header class="topbar no-print">
        <div class="topbar-main">
            <div class="brand-block">
                <span class="eyebrow">Analytics</span>
                <div class="brand">Performance Reports</div>
                <div class="brand-sub">Occupancy and revenue insight dashboard</div>
            </div>
            <div class="brand-meta">${sessionScope.username} | <%= role %></div>
        </div>
        <nav class="nav-links">
            <a class="btn btn-link" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/reservation">Reservations</a>
            <% if (canManageReservations) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/bill">Billing</a>
            <% } %>
            <a class="btn btn-link active" href="<%=request.getContextPath()%>/report">Reports</a>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/help">Help</a>
            <a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Logout</a>
        </nav>
    </header>

    <section class="page-header">
        <h1 class="page-title">Reports</h1>
        <p class="page-subtitle">Occupancy and revenue performance generated from current reservation records.</p>
    </section>

    <div class="action-row no-print">
        <a class="btn btn-secondary" href="<%=request.getContextPath()%>/report?export=csv">Export CSV</a>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <section class="card no-print">
        <h2 class="section-title">Report Summary</h2>
        <div class="quick-grid">
            <div class="quick-item">
                <strong>Occupancy Rate</strong>
                <span><%= String.format("%.2f", occupancyRate) %>% active room usage.</span>
            </div>
            <div class="quick-item">
                <strong>Total Revenue</strong>
                <span>$<%= revenue != null ? revenue.get("totalRevenue") : 0 %> collected.</span>
            </div>
            <div class="quick-item">
                <strong>Reservation Volume</strong>
                <span><%= revenue != null ? revenue.get("reservationCount") : 0 %> reservations in report scope.</span>
            </div>
            <% if (canAccessReports) { %>
            <div class="quick-item">
                <strong>Export Ready</strong>
                <span>Download CSV for external review and submission.</span>
            </div>
            <% } %>
        </div>
    </section>

    <section class="grid-2">
        <article class="card">
            <h2 class="section-title">Occupancy Report</h2>
            <div class="stack">
                <div class="bill-item">
                    <strong>Total Rooms</strong>
                    <span><%= occupancy != null ? occupancy.get("totalRooms") : 0 %></span>
                </div>
                <div class="bill-item">
                    <strong>Occupied Rooms</strong>
                    <span><%= occupancy != null ? occupancy.get("occupiedRooms") : 0 %></span>
                </div>
                <div class="bill-item">
                    <strong>Available Rooms</strong>
                    <span><%= occupancy != null ? occupancy.get("availableRooms") : 0 %></span>
                </div>
                <div class="bill-item">
                    <strong>Occupancy Rate</strong>
                    <span><%= String.format("%.2f", occupancyRate) %>%</span>
                    <div class="progress-wrap">
                        <div class="progress-track">
                            <div class="progress-bar" style="width:<%= String.format(java.util.Locale.US, "%.2f", occupancyRate) %>%;"></div>
                        </div>
                    </div>
                </div>
            </div>
        </article>

        <article class="card">
            <h2 class="section-title">Revenue Report</h2>
            <div class="stack">
                <div class="bill-item">
                    <strong>Total Revenue</strong>
                    <span>$<%= revenue != null ? revenue.get("totalRevenue") : 0 %></span>
                </div>
                <div class="bill-item">
                    <strong>Reservation Count</strong>
                    <span><%= revenue != null ? revenue.get("reservationCount") : 0 %></span>
                </div>
            </div>
        </article>
    </section>
</div>
</body>
</html>
