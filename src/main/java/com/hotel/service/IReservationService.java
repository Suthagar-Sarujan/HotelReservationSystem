package com.hotel.service;

import com.hotel.model.Reservation;
import com.hotel.model.enums.RoomType;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IReservationService {
    Reservation addReservation(Reservation reservation);
    Reservation getReservation(int id);
    Reservation getReservationByNo(String reservationNo);
    List<Reservation> getAllReservations();
    List<Reservation> searchByGuestName(String guestName);
    boolean updateReservation(Reservation reservation);
    boolean cancelReservation(int id);
    double calculateBill(int reservationId);
    Map<String, Object> printBill(int reservationId);
    double getRoomRate(RoomType roomType);
    List<Reservation> getReservationsByDateRange(Date startDate, Date endDate);
    Map<String, Object> getDashboardStats();
}