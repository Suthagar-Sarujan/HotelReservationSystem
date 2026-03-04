package com.hotel.web.rest;

import com.google.gson.Gson;
import com.hotel.model.Reservation;
import com.hotel.service.IReservationService;
import com.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ReservationWebService extends HttpServlet {
    private IReservationService reservationService;
    private Gson gson;

    @Override
    public void init() {
        try {
            reservationService = new ReservationServiceImpl();
        } catch (RuntimeException ex) {
            reservationService = null;
        }
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();

        if (!ensureServiceAvailable(response, out)) {
            return;
        }

        try {
            if (pathInfo == null || "/".equals(pathInfo)) {
                List<Reservation> reservations = reservationService.getAllReservations();
                out.print(gson.toJson(reservations));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Reservation reservation = reservationService.getReservation(id);
                if (reservation != null) {
                    out.print(gson.toJson(reservation));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Reservation not found\"}");
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid reservation ID\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (!ensureServiceAvailable(response, out)) {
            return;
        }

        try {
            StringBuilder payload = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                payload.append(line);
            }

            Reservation reservation = gson.fromJson(payload.toString(), Reservation.class);
            Reservation saved = reservationService.addReservation(reservation);
            response.setStatus(HttpServletResponse.SC_CREATED);
            out.print(gson.toJson(saved));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String pathInfo = request.getPathInfo();

        if (!ensureServiceAvailable(response, out)) {
            return;
        }

        try {
            if (pathInfo == null || "/".equals(pathInfo)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Missing reservation ID\"}");
                return;
            }

            int id = Integer.parseInt(pathInfo.substring(1));
            boolean deleted = reservationService.cancelReservation(id);
            if (deleted) {
                out.print("{\"success\":true,\"message\":\"Reservation deleted\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"success\":false,\"message\":\"Reservation not found\"}");
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Invalid reservation ID\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private boolean ensureServiceAvailable(HttpServletResponse response, PrintWriter out) {
        if (reservationService != null) {
            return true;
        }

        try {
            reservationService = new ReservationServiceImpl();
            return true;
        } catch (RuntimeException ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Database is not configured. Update src/main/resources/database.properties\"}");
            return false;
        }
    }
}
