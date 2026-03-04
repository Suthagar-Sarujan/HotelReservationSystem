package com.hotel.model.enums;

public enum RoomType {
    SINGLE(100.0, "Single Room - 1 Bed"),
    DOUBLE(150.0, "Double Room - 1 Double Bed"),
    TWIN(160.0, "Twin Room - 2 Single Beds"),
    SUITE(250.0, "Suite - Living Area + Bedroom"),
    DELUXE(350.0, "Deluxe Suite - Premium Amenities");

    private final double baseRate;
    private final String description;

    RoomType(double baseRate, String description) {
        this.baseRate = baseRate;
        this.description = description;
    }

    public double getBaseRate() { return baseRate; }
    public String getDescription() { return description; }
}