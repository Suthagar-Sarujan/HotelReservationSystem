<%@ page import="java.util.Map" %>
<%
    Map<String, Object> occupancy = (Map<String, Object>) request.getAttribute("occupancy");
    Map<String, Object> revenue = (Map<String, Object>) request.getAttribute("revenue");
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
        <div class="brand">Analytics</div>
        <nav class="nav-links">
            <a class="btn btn-link" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/reservation">Reservations</a>
            <a class="btn btn-link active" href="<%=request.getContextPath()%>/report">Reports</a>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/help">Help</a>
        </nav>
    </header>

    <section class="page-header">
        <h1 class="page-title">Reports</h1>
        <p class="page-subtitle">Occupancy and revenue performance for current reservation data.</p>
    </section>

    <div class="action-row no-print">
        <a class="btn btn-secondary" href="<%=request.getContextPath()%>/report?export=csv">Export CSV</a>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

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
