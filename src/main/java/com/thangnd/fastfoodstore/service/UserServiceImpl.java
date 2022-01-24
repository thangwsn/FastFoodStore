package com.thangnd.fastfoodstore.service;

import com.thangnd.fastfoodstore.entity.user.User;
import com.thangnd.fastfoodstore.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Override
    public User getUserByEmail(String username) {
        return userRepo.findByEmail(username);
    }
}
