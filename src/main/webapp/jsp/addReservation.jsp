<%@ page import="com.hotel.model.enums.RoomType" %>
<%
    RoomType[] roomTypes = (RoomType[]) request.getAttribute("roomTypes");
    if (roomTypes == null) {
        roomTypes = RoomType.values();
    }
    String role = String.valueOf(session.getAttribute("role"));
    boolean canAccessReports = "ADMIN".equalsIgnoreCase(role) || "MANAGER".equalsIgnoreCase(role);
    boolean canManageReservations = "ADMIN".equalsIgnoreCase(role) || "RECEPTION".equalsIgnoreCase(role);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Reservation</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>
<div class="app-shell">
    <header class="topbar no-print">
        <div class="brand">Reservation Workflow</div>
        <nav class="nav-links">
            <a class="btn btn-link" href="<%=request.getContextPath()%>/dashboard">Dashboard</a>
            <a class="btn btn-link active" href="<%=request.getContextPath()%>/reservation/add">New Reservation</a>
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
        <h1 class="page-title">Add Reservation</h1>
        <p class="page-subtitle">Capture complete guest details and booking dates in one step.</p>
    </section>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <section class="card">
        <h2 class="section-title">Guest and Booking Information</h2>
        <form class="form-grid" action="<%=request.getContextPath()%>/reservation/add" method="post" onsubmit="return validateReservationForm()">
            <div class="field">
                <label for="guestName">Guest Name</label>
                <input id="guestName" type="text" name="guestName" value="<%= request.getParameter("guestName") != null ? request.getParameter("guestName") : "" %>" placeholder="Full name" required/>
            </div>

            <div class="field">
                <label for="contactNo">Contact Number</label>
                <input id="contactNo" type="text" name="contactNo" value="<%= request.getParameter("contactNo") != null ? request.getParameter("contactNo") : "" %>" placeholder="10-12 digits" required/>
            </div>

            <div class="field full-width">
                <label for="address">Address</label>
                <input id="address" type="text" name="address" value="<%= request.getParameter("address") != null ? request.getParameter("address") : "" %>" placeholder="Street, city, country" required/>
            </div>

            <div class="field">
                <label for="email">Email</label>
                <input id="email" type="email" name="email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" placeholder="guest@example.com"/>
            </div>

            <div class="field">
                <label for="roomType">Room Type</label>
                <select id="roomType" name="roomType" required>
                    <option value="">Select room type</option>
                    <%
                        String selectedRoomType = request.getParameter("roomType");
                        for (RoomType roomType : roomTypes) {
                    %>
                    <option value="<%= roomType.name() %>" <%= roomType.name().equals(selectedRoomType) ? "selected" : "" %>><%= roomType.name() %></option>
                    <% } %>
                </select>
            </div>

            <div class="field">
                <label for="checkInDate">Check-In Date</label>
                <input id="checkInDate" type="date" name="checkInDate" value="<%= request.getParameter("checkInDate") != null ? request.getParameter("checkInDate") : "" %>" required/>
            </div>

            <div class="field">
                <label for="checkOutDate">Check-Out Date</label>
                <input id="checkOutDate" type="date" name="checkOutDate" value="<%= request.getParameter("checkOutDate") != null ? request.getParameter("checkOutDate") : "" %>" required/>
            </div>

            <div class="field">
                <label for="numGuests">Number of Guests</label>
                <input id="numGuests" type="number" name="numGuests" value="<%= request.getParameter("numGuests") != null ? request.getParameter("numGuests") : "" %>" min="1" required/>
            </div>

            <div class="field full-width">
                <label for="specialRequests">Special Requests</label>
                <textarea id="specialRequests" name="specialRequests" placeholder="Airport pickup, extra bed, dietary preferences, etc."><%= request.getParameter("specialRequests") != null ? request.getParameter("specialRequests") : "" %></textarea>
            </div>

            <div class="action-row full-width">
                <button class="btn btn-primary" type="submit">Create Reservation</button>
                <a class="btn btn-secondary" href="<%=request.getContextPath()%>/dashboard">Back to Dashboard</a>
            </div>
        </form>
    </section>
</div>
<script src="<%=request.getContextPath()%>/js/validation.js"></script>
</body>
</html>
