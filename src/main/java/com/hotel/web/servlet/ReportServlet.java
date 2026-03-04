package com.hotel.web.servlet;

import com.hotel.model.Reservation;
import com.hotel.service.IReservationService;
import com.hotel.service.ReportingService;
import com.hotel.service.impl.ReservationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            IReservationService reservationService = new ReservationServiceImpl();
            ReportingService reportingService = new ReportingService();
            List<Reservation> reservations = reservationService.getAllReservations();
            Map<String, Object> occupancy = reportingService.generateOccupancyReport(reservations);
            Map<String, Object> revenue = reportingService.generateRevenueReport(reservations);

            if ("csv".equalsIgnoreCase(request.getParameter("export"))) {
                exportCsv(response, occupancy, revenue);
                return;
            }

            request.setAttribute("occupancy", occupancy);
            request.setAttribute("revenue", revenue);
            request.setAttribute("reservations", reservations);
        } catch (RuntimeException ex) {
            request.setAttribute("error", "Database is not configured. Update src/main/resources/database.properties");
        }
        request.getRequestDispatcher("/jsp/reports.jsp").forward(request, response);
    }

    private void exportCsv(HttpServletResponse response, Map<String, Object> occupancy, Map<String, Object> revenue) throws IOException {
        String fileName = "hotel-report-" + LocalDate.now() + ".csv";
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        try (PrintWriter writer = response.getWriter()) {
            writer.println("Section,Metric,Value");
            writer.printf("Occupancy,Total Rooms,%s%n", occupancy.getOrDefault("totalRooms", 0));
            writer.printf("Occupancy,Occupied Rooms,%s%n", occupancy.getOrDefault("occupiedRooms", 0));
            writer.printf("Occupancy,Available Rooms,%s%n", occupancy.getOrDefault("availableRooms", 0));
            writer.printf("Occupancy,Occupancy Rate,%s%n", occupancy.getOrDefault("occupancyRate", 0));
            writer.printf("Revenue,Total Revenue,%s%n", revenue.getOrDefault("totalRevenue", 0));
            writer.printf("Revenue,Reservation Count,%s%n", revenue.getOrDefault("reservationCount", 0));
        }
    }
}
