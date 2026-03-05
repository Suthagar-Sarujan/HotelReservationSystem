<%@ page import="java.util.List" %>
<%@ page import="com.hotel.model.Reservation" %>
<%
    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
    Reservation selectedReservation = (Reservation) request.getAttribute("reservation");
    int reservationCount = reservations != null ? reservations.size() : 0;
    String role = String.valueOf(session.getAttribute("role"));
    boolean canAccessReports = "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
    boolean canManageReservations = "ADMIN".equalsIgnoreCase(role) || "RECEPTION".equalsIgnoreCase(role);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reservations</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<div class="app-shell">
    <header class="topbar no-print">
        <div class="topbar-main">
            <div class="brand-block">
                <span class="eyebrow">Reservations</span>
                <div class="brand">Guest Booking Register</div>
                <div class="brand-sub">Search, review, and manage reservation records</div>
            </div>
            <div class="brand-meta">${sessionScope.username} | <%= role %></div>
        </div>
        <nav class="nav-links">
            <a class="btn btn-link" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
            <% if (canManageReservations) { %>
            <a class="btn btn-link" href="<%=request.getContextPath()%>/reservation/add">New Reservation</a>
            <% } %>
            <a class="btn btn-link active" href="<%=request.getContextPath()%>/reservation">View Reservations</a>
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
        <h1 class="page-title">Reservation List</h1>
        <p class="page-subtitle">Search guests, inspect booking details, and manage reservation lifecycle actions.</p>
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

    <% if (selectedReservation != null) { %>
    <div class="alert alert-info">
        Viewing reservation <strong><%= selectedReservation.getReservationNo() %></strong> for <strong><%= selectedReservation.getGuestName() %></strong>.
        <a href="<%=request.getContextPath()%>/reservation">Show all reservations</a>
    </div>
    <% } %>

    <section class="card no-print">
        <h2 class="section-title">Reservation Overview</h2>
        <div class="quick-grid">
            <div class="quick-item">
                <strong>Loaded Records</strong>
                <span><%= reservationCount %> reservation(s) in current view.</span>
            </div>
            <div class="quick-item">
                <strong>Current Role</strong>
                <span><%= role %> access is applied to available actions.</span>
            </div>
            <div class="quick-item">
                <strong>Billing Shortcut</strong>
                <span>Generate a bill directly from each reservation row.</span>
            </div>
            <% if (canAccessReports) { %>
            <div class="quick-item">
                <strong>Reporting Access</strong>
                <span>Use Reports for occupancy and revenue analysis.</span>
            </div>
            <% } %>
        </div>
    </section>

    <section class="card">
        <div class="action-row no-print">
            <form class="search-form" method="get" action="<%=request.getContextPath()%>/reservation/search">
                <input type="text" name="guestName" value="<%= request.getParameter("guestName") != null ? request.getParameter("guestName") : "" %>" placeholder="Search by guest name"/>
                <button class="btn btn-primary" type="submit">Search</button>
                <a class="btn btn-secondary" href="<%=request.getContextPath()%>/reservation">Reset</a>
            </form>
        </div>

        <div class="table-shell">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>No</th>
                    <th>Guest</th>
                    <th>Contact</th>
                    <th>Room</th>
                    <th>Check-In</th>
                    <th>Check-Out</th>
                    <th>Total</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (reservations != null && !reservations.isEmpty()) {
                        for (Reservation r : reservations) {
                %>
                <tr>
                    <td><%= r.getId() %></td>
                    <td><%= r.getReservationNo() %></td>
                    <td><%= r.getGuestName() %></td>
                    <td><%= r.getContactNo() %></td>
                    <td><%= r.getRoomType() %></td>
                    <td><%= r.getCheckInDate() %></td>
                    <td><%= r.getCheckOutDate() %></td>
                    <td>$<%= r.getTotalAmount() %></td>
                    <td class="actions no-print">
                        <a class="btn btn-secondary btn-sm" href="<%=request.getContextPath()%>/reservation/view?id=<%=r.getId()%>">View</a>
                        <% if (canManageReservations) { %>
                        <a class="btn btn-secondary btn-sm" href="<%=request.getContextPath()%>/bill?reservationId=<%=r.getId()%>">Bill</a>
                        <form class="inline-form" method="post" action="<%=request.getContextPath()%>/reservation/delete" onsubmit="return confirm('Delete this reservation?');">
                            <input type="hidden" name="id" value="<%=r.getId()%>">
                            <button class="btn btn-danger btn-sm" type="submit">Delete</button>
                        </form>
                        <% } %>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="9" class="empty-state">No reservations found for the selected filter.</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </section>
</div>
</body>
</html>
