package com.hotel.service;

import com.hotel.model.User;

public interface IUserService {
    User authenticate(String username, String password);
    void logLogin(int userId, String ipAddress);
}