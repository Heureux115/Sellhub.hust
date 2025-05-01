package com.example.shop.service;

import org.springframework.stereotype.Service;
import com.example.shop.repository.UserRepository;
import com.example.shop.model.User;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void save(User user) {
        userRepo.save(user);
    }
}

