package com.hotel.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationHelperTest {
    private ValidationHelper validationHelper;

    @Before
    public void setUp() {
        validationHelper = new ValidationHelper();
    }

    @Test
    public void validateEmail_shouldAcceptValidEmail() {
        assertTrue(validationHelper.validateEmail("guest@hotel.com"));
    }

    @Test
    public void validateEmail_shouldRejectInvalidEmail() {
        assertFalse(validationHelper.validateEmail("guest-email"));
    }

    @Test
    public void validatePhone_shouldAccept10To12Digits() {
        assertTrue(validationHelper.validatePhone("1234567890"));
        assertTrue(validationHelper.validatePhone("123456789012"));
    }

    @Test
    public void validatePhone_shouldRejectBadPhone() {
        assertFalse(validationHelper.validatePhone("1234"));
        assertFalse(validationHelper.validatePhone(""));
    }

    @Test
    public void validateDates_shouldAcceptFutureRange() {
        Date checkIn = new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000);
        Date checkOut = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000);
        assertTrue(validationHelper.validateDates(checkIn, checkOut));
    }

    @Test
    public void validateDates_shouldAcceptSameDayCheckIn() {
        Date checkIn = new Date();
        Date checkOut = new Date(System.currentTimeMillis() + 24L * 60 * 60 * 1000);
        assertTrue(validationHelper.validateDates(checkIn, checkOut));
    }

    @Test
    public void validateDates_shouldRejectPastCheckIn() {
        Date checkIn = new Date(System.currentTimeMillis() - 24L * 60 * 60 * 1000);
        Date checkOut = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000);
        assertFalse(validationHelper.validateDates(checkIn, checkOut));
    }
}
