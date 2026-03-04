package com.hotel.pattern.observer;

import com.hotel.model.Reservation;

public class EmailNotifier implements Observer {
    @Override
    public void update(String eventType, Reservation reservation) {
        String subject;
        String body;

        switch (eventType) {
            case "CREATE":
                subject = "Reservation Confirmation";
                body = String.format("Dear %s, your reservation #%s has been confirmed.",
                        reservation.getGuestName(), reservation.getReservationNo());
                break;
            case "UPDATE":
                subject = "Reservation Updated";
                body = String.format("Dear %s, your reservation #%s has been updated.",
                        reservation.getGuestName(), reservation.getReservationNo());
                break;
            case "CANCEL":
                subject = "Reservation Cancelled";
                body = String.format("Dear %s, your reservation #%s has been cancelled.",
                        reservation.getGuestName(), reservation.getReservationNo());
                break;
            default:
                subject = "Reservation Notification";
                body = "Reservation event occurred.";
                break;
        }

        System.out.println("Sending email to " + reservation.getEmail());
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
