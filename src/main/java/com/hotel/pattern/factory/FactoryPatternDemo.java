package com.hotel.pattern.factory;

import com.hotel.service.IReservationService;
import com.hotel.service.IUserService;

public class FactoryPatternDemo {
    public static void demonstrateFactoryPattern() {

        ServiceFactory factory = ServiceFactory.getInstance();

        IReservationService reservationService = factory.getReservationService();

        IUserService userService = factory.getUserService();

        IUserService userService2 = factory.getUserService();
        boolean isSameInstance = (userService == userService2);
        System.out.println("Same instance: " + isSameInstance); // true
    }
}

