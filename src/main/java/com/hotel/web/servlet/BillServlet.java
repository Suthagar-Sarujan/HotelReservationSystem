package com.hotel.web.servlet;

import com.hotel.service.IReservationService;
import com.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class BillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reservationId = request.getParameter("reservationId");
        try {
            IReservationService reservationService = new ReservationServiceImpl();
            if (reservationId != null && !reservationId.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(reservationId);
                    Map<String, Object> bill = reservationService.printBill(id);
                    request.setAttribute("bill", bill);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
        } catch (RuntimeException ex) {
            request.setAttribute("error", "Database is not configured. Update src/main/resources/database.properties");
        }
        request.getRequestDispatcher("/jsp/calculateBill.jsp").forward(request, response);
    }
}
