package com.hotel.pattern.dao;

import com.hotel.dao.UserDAO;
import com.hotel.dao.ReservationDAO;
import com.hotel.dao.impl.UserDAOImpl;
import com.hotel.dao.impl.ReservationDAOImpl;
import com.hotel.model.User;
import com.hotel.model.Reservation;

import java.util.List;


public class DAOPatternDemo {

    public static void demonstrateDAOPattern() {
        try {
            UserDAO userDAO = new UserDAOImpl();
            ReservationDAO reservationDAO = new ReservationDAOImpl();

            User user = userDAO.findByUsername("admin");

            List<Reservation> reservations = reservationDAO.findAll();

            if (user != null) {
                System.out.println("User found: " + user.getUsername());
                userDAO.logLogin(user.getId(), "192.168.1.1");
            }

            System.out.println("Total reservations: " + reservations.size());
        } catch (RuntimeException e) {
            System.out.println("Database not configured: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        System.out.println("=== DAO PATTERN DEMONSTRATION ===");
        demonstrateDAOPattern();
    }
}
