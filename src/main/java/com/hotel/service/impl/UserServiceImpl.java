package com.hotel.service.impl;

import com.hotel.dao.UserDAO;
import com.hotel.dao.impl.UserDAOImpl;
import com.hotel.model.User;
import com.hotel.service.IUserService;

public class UserServiceImpl implements IUserService {
    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public User authenticate(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }

        // DEMO/DEVELOPMENT MODE: Using per-role demo passwords for testing purposes
        // PRODUCTION NOTE: Replace with proper password hashing (BCrypt/Argon2) against stored credentials
        return verifyPassword(user.getUsername(), password) ? user : null;
    }

    @Override
    public void logLogin(int userId, String ipAddress) {
        userDAO.logLogin(userId, ipAddress);
    }

    /**
     * Verifies the provided password against demo credentials.
     * DEMO MODE: Simple string comparison per demo user
     * PRODUCTION: Replace with hashed password verification (e.g., BCrypt.checkpw)
     */
    private boolean verifyPassword(String username, String providedPassword) {
        if (username == null || providedPassword == null) {
            return false;
        }

        String normalized = username.trim().toLowerCase();
        switch (normalized) {
            case "admin":
                return "admin123".equals(providedPassword);
            case "manager":
                return "manager123".equals(providedPassword);
            case "reception":
                return "reception123".equals(providedPassword);
            default:
                return false;
        }
    }
}
