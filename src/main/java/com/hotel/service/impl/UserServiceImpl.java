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

        // DEMO/DEVELOPMENT MODE: Using hardcoded password for testing purposes
        // PRODUCTION NOTE: This should be replaced with proper password hashing (BCrypt/Argon2)
        // Example: BCrypt.checkpw(password, user.getPassword())
        return verifyPassword(password, user.getPassword()) ? user : null;
    }

    @Override
    public void logLogin(int userId, String ipAddress) {
        userDAO.logLogin(userId, ipAddress);
    }

    /**
     * Verifies the provided password against the stored password.
     * DEMO MODE: Uses simple string comparison
     * PRODUCTION: Should use BCrypt.checkpw() or similar secure hashing
     */
    @SuppressWarnings("unused")
    private boolean verifyPassword(String providedPassword, String storedPassword) {
        // For demo/testing: accept "admin123" for any user
        // In production, compare with actual hashed password from database
        // Example: return BCrypt.checkpw(providedPassword, storedPassword);
        return "admin123".equals(providedPassword);
    }
}
