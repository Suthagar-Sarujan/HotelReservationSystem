package com.hotel.pattern.observer;

import com.hotel.model.Reservation;

public interface Observer {
    void update(String eventType, Reservation reservation);
}