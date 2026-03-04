package com.hotel.pattern.observer;

import com.hotel.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationSubject implements Subject {
    private final List<Observer> observers = new ArrayList<>();
    private static ReservationSubject instance;

    private ReservationSubject() {
    }

    public static synchronized ReservationSubject getInstance() {
        if (instance == null) {
            instance = new ReservationSubject();
        }
        return instance;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String eventType, Reservation reservation) {
        for (Observer observer : observers) {
            observer.update(eventType, reservation);
        }
    }

    public void reservationCreated(Reservation reservation) {
        notifyObservers("CREATE", reservation);
    }

    public void reservationUpdated(Reservation reservation) {
        notifyObservers("UPDATE", reservation);
    }

    public void reservationCancelled(Reservation reservation) {
        notifyObservers("CANCEL", reservation);
    }
}
