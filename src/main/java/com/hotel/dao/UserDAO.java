package com.hotel.dao;

import com.hotel.model.User;

public interface UserDAO {
    User findByUsername(String username);
    void logLogin(int userId, String ipAddress);
}
