package com.hotel.service;

import com.hotel.model.enums.RoomType;

import java.util.EnumMap;
import java.util.Map;

public class RoomRateService {
    private final Map<RoomType, Double> customRates = new EnumMap<>(RoomType.class);

    public double getRate(RoomType roomType) {
        if (roomType == null) {
            throw new IllegalArgumentException("Room type is required");
        }
        return customRates.getOrDefault(roomType, roomType.getBaseRate());
    }

    public void updateRate(RoomType roomType, double rate) {
        if (roomType == null || rate <= 0) {
            throw new IllegalArgumentException("Invalid room rate update");
        }
        customRates.put(roomType, rate);
    }

    public double getSeasonalRate(RoomType roomType, double multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be greater than zero");
        }
        return getRate(roomType) * multiplier;
    }
}
