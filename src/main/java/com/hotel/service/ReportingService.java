package com.hotel.service;

import com.hotel.config.WebConfig;
import com.hotel.model.Reservation;
import com.hotel.util.ReportGenerator;

import java.util.List;
import java.util.Map;

public class ReportingService {
    private final ReportGenerator reportGenerator;

    public ReportingService() {
        this.reportGenerator = new ReportGenerator();
    }

    public Map<String, Object> generateOccupancyReport(List<Reservation> reservations) {
        return reportGenerator.generateOccupancy(reservations, WebConfig.TOTAL_ROOMS);
    }

    public Map<String, Object> generateRevenueReport(List<Reservation> reservations) {
        return reportGenerator.generateRevenue(reservations);
    }
}
