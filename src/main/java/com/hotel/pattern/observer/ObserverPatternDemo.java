package com.hotel.pattern.observer;

import com.hotel.model.Reservation;

public class ObserverPatternDemo {

    public static void demonstrateObserverPattern() {

        ReservationSubject subject = ReservationSubject.getInstance();

        Observer emailNotifier = new EmailNotifier();
        Observer smsNotifier = new SMSNotifier();
        Observer loggingObserver = new LoggingObserver();

        subject.registerObserver(emailNotifier);
        subject.registerObserver(smsNotifier);
        subject.registerObserver(loggingObserver);

        Reservation reservation = createSampleReservation();

        subject.reservationCreated(reservation);    // All 3 observers receive notification
        subject.reservationUpdated(reservation);    // All 3 observers receive notification
        subject.reservationCancelled(reservation);  // All 3 observers receive notification

        subject.removeObserver(smsNotifier);

        subject.reservationCreated(reservation);    // Only 2 observers receive notification
    }


    private static Reservation createSampleReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationNo("RES-2024-001");
        reservation.setGuestName("John Doe");
        reservation.setEmail("john.doe@example.com");
        reservation.setContactNo("+1234567890");
        return reservation;
    }
}

class SMSNotifier implements Observer {
    @Override
    public void update(String eventType, Reservation reservation) {
        String message = formatSMSMessage(eventType, reservation);
        sendSMS(reservation.getContactNo(), message);
    }

    private String formatSMSMessage(String eventType, Reservation reservation) {
        switch (eventType) {
            case "CREATE":
                return "Reservation " + reservation.getReservationNo() + " confirmed!";
            case "UPDATE":
                return "Reservation " + reservation.getReservationNo() + " updated.";
            case "CANCEL":
                return "Reservation " + reservation.getReservationNo() + " cancelled.";
            default:
                return "Reservation notification.";
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        // SMS sending logic using SMS gateway API
        System.out.println("SMS to " + phoneNumber + ": " + message);
    }
}


class LoggingObserver implements Observer {
    @Override
    public void update(String eventType, Reservation reservation) {
        String logMessage = String.format(
            "[%s] Reservation %s - Guest: %s, Event: %s",
            java.time.LocalDateTime.now(),
            reservation.getReservationNo(),
            reservation.getGuestName(),
            eventType
        );
        logToDatabase(logMessage);
    }

    private void logToDatabase(String message) {
        // Log to database or file system
        System.out.println("LOG: " + message);
    }
}

