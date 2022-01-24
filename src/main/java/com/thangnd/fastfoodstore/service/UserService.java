package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.user.User;

public interface UserService {
    User getUserByEmail(String username);
}
