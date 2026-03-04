package com.hotel.model;

import com.hotel.model.enums.RoomType;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Reservation extends BaseEntity {
    private String reservationNo;
    private String guestName;
    private String address;
    private String contactNo;
    private String email;
    private RoomType roomType;
    private Date checkInDate;
    private Date checkOutDate;
    private int numNights;
    private double totalAmount;
    private String specialRequests;
    private int numGuests;

    public Reservation() {
        super();
        this.reservationNo = generateReservationNo();
    }

    private String generateReservationNo() {
        return "RES" + System.currentTimeMillis();
    }

    public int calculateNights() {
        if (checkInDate != null && checkOutDate != null) {
            long diffInMillis = checkOutDate.getTime() - checkInDate.getTime();
            this.numNights = (int) TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
            return this.numNights;
        }
        return 0;
    }

    public double calculateAmount() {
        if (roomType == null) {
            return 0;
        }
        this.totalAmount = this.numNights * this.roomType.getBaseRate();
        return this.totalAmount;
    }

    public boolean validateDates() {
        if (checkInDate == null || checkOutDate == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        LocalDate checkIn = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOut = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return !checkIn.isBefore(today) && checkOut.isAfter(checkIn);
    }

    public String getReservationNo() { return reservationNo; }
    public void setReservationNo(String reservationNo) { this.reservationNo = reservationNo; }
    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public int getNumNights() { return numNights; }
    public void setNumNights(int numNights) { this.numNights = numNights; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }
    public int getNumGuests() { return numGuests; }
    public void setNumGuests(int numGuests) { this.numGuests = numGuests; }

    @Override
    public String toString() {
        return String.format("Reservation #%s: %s - %s, %d nights, $%.2f",
                reservationNo, guestName, roomType, numNights, totalAmount);
    }
}
