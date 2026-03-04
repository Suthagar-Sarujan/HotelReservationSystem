package com.hotel.web.servlet;

import com.hotel.model.Reservation;
import com.hotel.model.enums.RoomType;
import com.hotel.service.IReservationService;
import com.hotel.service.impl.ReservationServiceImpl;
import com.hotel.util.ValidationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReservationServlet extends HttpServlet {
    private ValidationHelper validator;
    private SimpleDateFormat dateFormat;

    @Override
    public void init() {
        validator = new ValidationHelper();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IReservationService reservationService = createReservationService();
        if (reservationService == null) {
            request.setAttribute("error", "Database is not configured. Update src/main/resources/database.properties");
            request.getRequestDispatcher("/jsp/viewReservation.jsp").forward(request, response);
            return;
        }

        String pathInfo = request.getPathInfo();

        if (pathInfo == null || "/".equals(pathInfo)) {
            List<Reservation> reservations = reservationService.getAllReservations();
            request.setAttribute("reservations", reservations);
            request.getRequestDispatcher("/jsp/viewReservation.jsp").forward(request, response);
            return;
        }

        if ("/add".equals(pathInfo)) {
            request.setAttribute("roomTypes", RoomType.values());
            request.getRequestDispatcher("/jsp/addReservation.jsp").forward(request, response);
            return;
        }

        if ("/search".equals(pathInfo)) {
            String guestName = request.getParameter("guestName");
            request.setAttribute("reservations", reservationService.searchByGuestName(guestName));
            request.getRequestDispatcher("/jsp/viewReservation.jsp").forward(request, response);
            return;
        }

        if ("/view".equals(pathInfo)) {
            String idParam = request.getParameter("id");
            try {
                int id = Integer.parseInt(idParam);
                Reservation reservation = reservationService.getReservation(id);
                if (reservation == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                request.setAttribute("reservation", reservation);
                request.setAttribute("reservations", List.of(reservation));
                request.getRequestDispatcher("/jsp/viewReservation.jsp").forward(request, response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IReservationService reservationService = createReservationService();
        if (reservationService == null) {
            request.getSession().setAttribute("error", "Database is not configured. Update src/main/resources/database.properties");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        String pathInfo = request.getPathInfo();

        if ("/update".equals(pathInfo)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Reservation reservation = extractReservationFromRequest(request);
                reservation.setId(id);
                reservation.setUpdatedAt(new Date());
                boolean updated = reservationService.updateReservation(reservation);
                request.getSession().setAttribute(
                        updated ? "message" : "error",
                        updated ? "Reservation updated successfully" : "Failed to update reservation"
                );
                response.sendRedirect(request.getContextPath() + "/reservation/view?id=" + id);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            return;
        }

        if ("/delete".equals(pathInfo)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean deleted = reservationService.cancelReservation(id);
                request.getSession().setAttribute(
                        deleted ? "message" : "error",
                        deleted ? "Reservation deleted successfully" : "Reservation not found"
                );
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Invalid reservation ID");
            }
            response.sendRedirect(request.getContextPath() + "/reservation");
            return;
        }

        try {
            Reservation reservation = extractReservationFromRequest(request);
            if (!validator.validateReservation(reservation)) {
                request.setAttribute("error", "Invalid reservation data.");
                request.setAttribute("roomTypes", RoomType.values());
                request.getRequestDispatcher("/jsp/addReservation.jsp").forward(request, response);
                return;
            }
            Reservation saved = reservationService.addReservation(reservation);
            request.getSession().setAttribute("message", "Reservation created: " + saved.getReservationNo());
            response.sendRedirect(request.getContextPath() + "/reservation");
        } catch (Exception e) {
            request.setAttribute("error", "Error creating reservation: " + e.getMessage());
            request.getRequestDispatcher("/jsp/addReservation.jsp").forward(request, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IReservationService reservationService = createReservationService();
        if (reservationService == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"success\":false,\"message\":\"Database not configured\"}");
            return;
        }

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            try {
                int id = Integer.parseInt(pathInfo.substring(1));
                boolean deleted = reservationService.cancelReservation(id);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                if (deleted) {
                    out.print("{\"success\":true}");
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"success\":false}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private Reservation extractReservationFromRequest(HttpServletRequest request) throws Exception {
        Reservation reservation = new Reservation();
        reservation.setGuestName(request.getParameter("guestName"));
        reservation.setAddress(request.getParameter("address"));
        reservation.setContactNo(request.getParameter("contactNo"));
        reservation.setEmail(request.getParameter("email"));
        reservation.setSpecialRequests(request.getParameter("specialRequests"));

        String roomTypeStr = request.getParameter("roomType");
        if (roomTypeStr != null && !roomTypeStr.isEmpty()) {
            reservation.setRoomType(RoomType.valueOf(roomTypeStr));
        }

        String checkInStr = request.getParameter("checkInDate");
        if (checkInStr != null && !checkInStr.isEmpty()) {
            reservation.setCheckInDate(dateFormat.parse(checkInStr));
        }

        String checkOutStr = request.getParameter("checkOutDate");
        if (checkOutStr != null && !checkOutStr.isEmpty()) {
            reservation.setCheckOutDate(dateFormat.parse(checkOutStr));
        }

        String numGuestsStr = request.getParameter("numGuests");
        if (numGuestsStr != null && !numGuestsStr.isEmpty()) {
            reservation.setNumGuests(Integer.parseInt(numGuestsStr));
        }

        return reservation;
    }

    private IReservationService createReservationService() {
        try {
            return new ReservationServiceImpl();
        } catch (RuntimeException ex) {
            return null;
        }
    }
}
