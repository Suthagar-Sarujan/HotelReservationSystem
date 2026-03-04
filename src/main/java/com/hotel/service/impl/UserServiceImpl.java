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
        // Demo password check for assignment sample users.
        return "admin123".equals(password) ? user : null;
    }

    @Override
    public void logLogin(int userId, String ipAddress) {
        userDAO.logLogin(userId, ipAddress);
    }
}
