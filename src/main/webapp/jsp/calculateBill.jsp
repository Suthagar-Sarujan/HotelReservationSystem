<%@ page import="java.util.Map" %>
<%
    Map<String, Object> bill = (Map<String, Object>) request.getAttribute("bill");
    String reservationId = request.getParameter("reservationId") != null ? request.getParameter("reservationId") : "";
    String role = String.valueOf(session.getAttribute("role"));
    boolean canAccessReports = "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
    boolean canManageReservations = "ADMIN".equalsIgnoreCase(role) || "RECEPTION".equalsIgnoreCase(role);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Calculate Bill</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<div class="app-shell">
    <header class="topbar no-print">
        <div class="brand">Billing</div>
        <nav class="nav-links">
            <a class="btn btn-link" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/reservation">Reservations</a>
            <% if (canManageReservations) { %>
            <a class="btn btn-link active" href="<%=request.getContextPath()%>/bill">Calculate Bill</a>
            <% } %>
            <% if (canAccessReports) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/report">Reports</a>
            <% } %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/help">Help</a>
            <a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Logout</a>
        </nav>
    </header>

    <section class="page-header">
        <h1 class="page-title">Calculate and Print Bill</h1>
        <p class="page-subtitle">Generate a complete bill by entering the reservation ID.</p>
    </section>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <section class="card no-print">
        <form class="grid-2" method="get" action="<%=request.getContextPath()%>/bill">
            <div class="field">
                <label for="reservationId">Reservation ID</label>
                <input id="reservationId" type="number" name="reservationId" value="<%= reservationId %>" placeholder="Enter reservation ID" required/>
            </div>
            <div class="field">
                <label>&nbsp;</label>
                <button class="btn btn-primary" type="submit">Generate Bill</button>
            </div>
        </form>
    </section>

    <% if (bill != null) { %>
    <section class="card">
        <h2 class="section-title">Bill Details</h2>
        <p class="section-subtitle">Review the values below before printing.</p>
        <div class="bill-grid">
            <div class="bill-item">
                <strong>Reservation No</strong>
                <span><%= bill.get("reservationNo") %></span>
            </div>
            <div class="bill-item">
                <strong>Guest Name</strong>
                <span><%= bill.get("guestName") %></span>
            </div>
            <div class="bill-item">
                <strong>Room Type</strong>
                <span><%= bill.get("roomType") %></span>
            </div>
            <div class="bill-item">
                <strong>Nights</strong>
                <span><%= bill.get("nights") %></span>
            </div>
            <div class="bill-item">
                <strong>Room Rate</strong>
                <span>$<%= bill.get("roomRate") %></span>
            </div>
            <div class="bill-item">
                <strong>Subtotal</strong>
                <span>$<%= bill.get("subtotal") %></span>
            </div>
            <div class="bill-item">
                <strong>Tax</strong>
                <span>$<%= bill.get("tax") %></span>
            </div>
            <div class="bill-item">
                <strong>Service Charge</strong>
                <span>$<%= bill.get("serviceCharge") %></span>
            </div>
            <div class="bill-item bill-total">
                <strong>Total Amount</strong>
                <span>$<%= bill.get("total") %></span>
            </div>
        </div>
        <div class="action-row bill-actions no-print">
            <button class="btn btn-primary" type="button" onclick="window.print()">Print Bill</button>
        </div>
    </section>
    <% } %>
</div>
</body>
</html>
