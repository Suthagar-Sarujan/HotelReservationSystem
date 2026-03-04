package com.hotel.util;

import com.hotel.model.Reservation;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGenerator {
    public Map<String, Object> generateOccupancy(List<Reservation> reservations, int totalRooms) {
        Map<String, Object> report = new HashMap<>();
        LocalDate today = LocalDate.now();
        long occupied = reservations.stream()
                .filter(r -> isActiveOnDate(r, today))
                .count();

        double occupancyRate = totalRooms <= 0 ? 0.0 : ((double) occupied / totalRooms) * 100.0;
        report.put("occupiedRooms", occupied);
        report.put("totalRooms", totalRooms);
        report.put("availableRooms", Math.max(0, totalRooms - occupied));
        report.put("occupancyRate", Math.min(100.0, occupancyRate));
        return report;
    }

    public Map<String, Object> generateRevenue(List<Reservation> reservations) {
        Map<String, Object> report = new HashMap<>();
        double totalRevenue = 0.0;
        for (Reservation reservation : reservations) {
            totalRevenue += reservation.getTotalAmount();
        }
        report.put("totalRevenue", totalRevenue);
        report.put("reservationCount", reservations.size());
        return report;
    }

    public byte[] exportToPDF(Map<String, Object> occupancy, Map<String, Object> revenue) {
        StringBuilder content = new StringBuilder();
        content.append("Hotel Reservation Report").append(System.lineSeparator());
        content.append("Generated On: ").append(LocalDate.now()).append(System.lineSeparator());
        content.append("Total Rooms: ").append(occupancy.getOrDefault("totalRooms", 0)).append(System.lineSeparator());
        content.append("Occupied Rooms: ").append(occupancy.getOrDefault("occupiedRooms", 0)).append(System.lineSeparator());
        content.append("Available Rooms: ").append(occupancy.getOrDefault("availableRooms", 0)).append(System.lineSeparator());
        content.append("Occupancy Rate: ").append(occupancy.getOrDefault("occupancyRate", 0)).append(System.lineSeparator());
        content.append("Total Revenue: ").append(revenue.getOrDefault("totalRevenue", 0)).append(System.lineSeparator());
        content.append("Reservation Count: ").append(revenue.getOrDefault("reservationCount", 0)).append(System.lineSeparator());
        return content.toString().getBytes(StandardCharsets.UTF_8);
    }

    private boolean isActiveOnDate(Reservation reservation, LocalDate date) {
        if (reservation == null || reservation.getCheckInDate() == null || reservation.getCheckOutDate() == null) {
            return false;
        }
        LocalDate checkIn = reservation.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOut = reservation.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return !checkIn.isAfter(date) && !checkOut.isBefore(date);
    }
}
