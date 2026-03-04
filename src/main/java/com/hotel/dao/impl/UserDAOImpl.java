package com.hotel.dao.impl;

import com.hotel.dao.UserDAO;
import com.hotel.model.User;

public class UserDAOImpl implements UserDAO {
    @Override
    public User findByUsername(String username) {
        if (username == null) {
            return null;
        }

        String normalized = username.trim().toLowerCase();
        if (!"admin".equals(normalized) && !"reception".equals(normalized) && !"manager".equals(normalized)) {
            return null;
        }

        User user = new User();
        if ("admin".equals(normalized)) {
            user.setId(1);
            user.setUsername("admin");
            user.setRole("ADMIN");
        } else if ("manager".equals(normalized)) {
            user.setId(2);
            user.setUsername("manager");
            user.setRole("MANAGER");
        } else {
            user.setId(3);
            user.setUsername("reception");
            user.setRole("RECEPTION");
        }
        return user;
    }

    @Override
    public void logLogin(int userId, String ipAddress) {
        // Demo implementation. Replace with DB insert if needed.
    }
}
