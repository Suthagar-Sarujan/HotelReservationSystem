package com.hotel.pattern.observer;

import com.hotel.model.Reservation;
import java.util.logging.Logger;
import java.util.logging.Level;

public class EmailNotifier implements Observer {
    private static final Logger LOGGER = Logger.getLogger(EmailNotifier.class.getName());

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

        sendEmail(reservation.getEmail(), subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        // Email sending logic would be implemented here using JavaMail API
        // For demonstration purposes, we log the email details
        LOGGER.log(Level.INFO, "Sending email to {0}", to);
        LOGGER.log(Level.INFO, "Subject: {0}", subject);
        LOGGER.log(Level.INFO, "Body: {0}", body);
    }
}
