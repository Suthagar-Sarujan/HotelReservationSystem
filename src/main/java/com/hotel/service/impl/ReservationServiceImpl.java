package com.hotel.service.impl;

import com.hotel.dao.ReservationDAO;
import com.hotel.dao.impl.ReservationDAOImpl;
import com.hotel.config.WebConfig;
import com.hotel.model.Reservation;
import com.hotel.model.enums.RoomType;
import com.hotel.service.IReservationService;
import com.hotel.service.RoomRateService;
import com.hotel.util.BillCalculator;
import com.hotel.util.ValidationHelper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReservationServiceImpl implements IReservationService {
    private final ReservationDAO reservationDAO;
    private final BillCalculator billCalculator;
    private final ValidationHelper validator;
    private final RoomRateService roomRateService;

    public ReservationServiceImpl() {
        this(new ReservationDAOImpl(), new BillCalculator(), new ValidationHelper(), new RoomRateService());
    }

    public ReservationServiceImpl(ReservationDAO reservationDAO, BillCalculator billCalculator, ValidationHelper validator) {
        this(reservationDAO, billCalculator, validator, new RoomRateService());
    }

    public ReservationServiceImpl(ReservationDAO reservationDAO, BillCalculator billCalculator, ValidationHelper validator, RoomRateService roomRateService) {
        this.reservationDAO = reservationDAO;
        this.billCalculator = billCalculator;
        this.validator = validator;
        this.roomRateService = roomRateService;
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        if (!validator.validateReservation(reservation)) {
            throw new IllegalArgumentException("Invalid reservation data");
        }
        reservation.calculateNights();
        reservation.calculateAmount();
        reservation.setTotalAmount(billCalculator.calculateTotal(reservation));
        return reservationDAO.save(reservation);
    }

    @Override
    public Reservation getReservation(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid reservation ID");
        }
        return reservationDAO.findById(id);
    }

    @Override
    public Reservation getReservationByNo(String reservationNo) {
        if (reservationNo == null || reservationNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Reservation number cannot be empty");
        }
        return reservationDAO.findByReservationNo(reservationNo);
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }

    @Override
    public List<Reservation> searchByGuestName(String guestName) {
        if (guestName == null || guestName.trim().isEmpty()) {
            return getAllReservations();
        }
        return reservationDAO.findByGuestName(guestName);
    }

    @Override
    public boolean updateReservation(Reservation reservation) {
        if (reservation == null || reservation.getId() <= 0) {
            return false;
        }
        if (!validator.validateReservation(reservation)) {
            return false;
        }
        reservation.calculateNights();
        reservation.calculateAmount();
        reservation.setTotalAmount(billCalculator.calculateTotal(reservation));
        return reservationDAO.update(reservation) != null;
    }

    @Override
    public boolean cancelReservation(int id) {
        return reservationDAO.delete(id);
    }

    @Override
    public double calculateBill(int reservationId) {
        Reservation reservation = getReservation(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }
        return billCalculator.calculateTotal(reservation);
    }

    @Override
    public Map<String, Object> printBill(int reservationId) {
        Reservation reservation = getReservation(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        BillCalculator.BillBreakdown breakdown = billCalculator.getDetailedBill(reservation);
        Map<String, Object> billDetails = new HashMap<>();
        billDetails.put("reservationNo", reservation.getReservationNo());
        billDetails.put("guestName", reservation.getGuestName());
        billDetails.put("roomType", reservation.getRoomType().getDescription());
        billDetails.put("checkIn", reservation.getCheckInDate());
        billDetails.put("checkOut", reservation.getCheckOutDate());
        billDetails.put("nights", breakdown.nights);
        billDetails.put("roomRate", breakdown.ratePerNight);
        billDetails.put("subtotal", breakdown.roomSubtotal);
        billDetails.put("tax", breakdown.tax);
        billDetails.put("serviceCharge", breakdown.serviceCharge);
        billDetails.put("total", breakdown.total);
        billDetails.put("generatedDate", new Date());
        return billDetails;
    }

    @Override
    public double getRoomRate(RoomType roomType) {
        return roomRateService.getRate(roomType);
    }

    @Override
    public List<Reservation> getReservationsByDateRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        return reservationDAO.findByDateRange(startDate, endDate);
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        List<Reservation> allReservations = getAllReservations();
        stats.put("totalReservations", allReservations.size());
        stats.put("totalRevenue", reservationDAO.getTotalRevenue());
        stats.put("occupancyRate", calculateOccupancyRate());

        Map<RoomType, Integer> roomDistribution = new HashMap<>();
        for (Reservation res : allReservations) {
            roomDistribution.merge(res.getRoomType(), 1, Integer::sum);
        }
        stats.put("roomDistribution", roomDistribution);
        stats.put("recentReservations", allReservations.stream().limit(5).collect(Collectors.toList()));
        return stats;
    }

    private double calculateOccupancyRate() {
        int totalRooms = WebConfig.TOTAL_ROOMS;
        if (totalRooms <= 0) {
            return 0.0;
        }

        long occupiedRooms = getAllReservations().stream()
                .filter(this::isActiveOnDate)
                .count();

        double occupancy = (double) occupiedRooms / totalRooms * 100;
        return Math.min(occupancy, 100.0);
    }

    private boolean isActiveOnDate(Reservation reservation) {
        if (reservation.getCheckInDate() == null || reservation.getCheckOutDate() == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        LocalDate checkIn = reservation.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOut = reservation.getCheckOutDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return !checkIn.isAfter(today) && !checkOut.isBefore(today);
    }
}
