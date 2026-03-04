package com.hotel.util;

import com.hotel.model.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

public class ValidationHelper {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10,12}$");

    public boolean validateLogin(String username, String password) {
        return username != null && !username.trim().isEmpty() && password != null && !password.trim().isEmpty();
    }

    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        String digits = phone.replaceAll("\\D", "");
        return PHONE_PATTERN.matcher(digits).matches();
    }

    public boolean validateDates(Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        LocalDate checkInDate = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOutDate = checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return !checkInDate.isBefore(today) && checkOutDate.isAfter(checkInDate);
    }

    public boolean validateRequired(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean validateReservation(Reservation reservation) {
        if (reservation == null) {
            return false;
        }
        return validateRequired(reservation.getGuestName(), reservation.getAddress(), reservation.getContactNo())
                && validatePhone(reservation.getContactNo())
                && validateEmail(reservation.getEmail())
                && validateDates(reservation.getCheckInDate(), reservation.getCheckOutDate())
                && reservation.getRoomType() != null
                && reservation.getNumGuests() > 0;
    }
}
