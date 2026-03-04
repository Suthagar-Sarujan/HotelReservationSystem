package com.hotel.pattern.observer;

import com.hotel.model.Reservation;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String eventType, Reservation reservation);
}