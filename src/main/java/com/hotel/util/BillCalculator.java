package com.hotel.util;

import com.hotel.model.Reservation;

public class BillCalculator {
    private static final double TAX_RATE = 0.10;
    private static final double SERVICE_CHARGE = 0.05;

    public double calculateTotal(Reservation reservation) {
        double roomCost = reservation.getNumNights() * reservation.getRoomType().getBaseRate();
        return roomCost + calculateTax(roomCost) + calculateServiceCharge(roomCost);
    }

    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }

    public double calculateServiceCharge(double amount) {
        return amount * SERVICE_CHARGE;
    }

    public BillBreakdown getDetailedBill(Reservation reservation) {
        BillBreakdown breakdown = new BillBreakdown();
        breakdown.roomType = reservation.getRoomType().getDescription();
        breakdown.nights = reservation.getNumNights();
        breakdown.ratePerNight = reservation.getRoomType().getBaseRate();
        breakdown.roomSubtotal = breakdown.nights * breakdown.ratePerNight;
        breakdown.tax = calculateTax(breakdown.roomSubtotal);
        breakdown.serviceCharge = calculateServiceCharge(breakdown.roomSubtotal);
        breakdown.total = breakdown.roomSubtotal + breakdown.tax + breakdown.serviceCharge;
        return breakdown;
    }

    public static class BillBreakdown {
        public String roomType;
        public int nights;
        public double ratePerNight;
        public double roomSubtotal;
        public double tax;
        public double serviceCharge;
        public double total;
    }
}
