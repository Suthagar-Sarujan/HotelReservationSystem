package com.hotel.dao;

import com.hotel.model.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationDAO {
    Reservation save(Reservation reservation);
    Reservation findById(int id);
    Reservation findByReservationNo(String reservationNo);
    List<Reservation> findByGuestName(String guestName);
    List<Reservation> findByDateRange(Date startDate, Date endDate);
    List<Reservation> findAll();
    Reservation update(Reservation reservation);
    boolean delete(int id);
    int getTotalCount();
    double getTotalRevenue();
}