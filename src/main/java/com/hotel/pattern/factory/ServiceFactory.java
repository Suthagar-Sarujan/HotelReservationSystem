package com.hotel.pattern.factory;

import com.hotel.service.IReservationService;
import com.hotel.service.IUserService;
import com.hotel.service.impl.ReservationServiceImpl;
import com.hotel.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static ServiceFactory instance;
    private IReservationService reservationService;
    private IUserService userService;

    private ServiceFactory() {
    }

    public static synchronized ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public IReservationService getReservationService() {
        if (reservationService == null) {
            reservationService = new ReservationServiceImpl();
        }
        return reservationService;
    }

    public IUserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}