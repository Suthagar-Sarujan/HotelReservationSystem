package com.hotel.web.servlet;

import com.hotel.service.IReservationService;
import com.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IReservationService reservationService = new ReservationServiceImpl();
            request.setAttribute("stats", reservationService.getDashboardStats());
        } catch (RuntimeException ex) {
            request.setAttribute("error", "Database is not configured. Update src/main/resources/database.properties");
        }
        request.getRequestDispatcher("/jsp/dashboard.jsp").forward(request, response);
    }
}
