package com.example.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.shop.repository.UserRepository;
import com.example.shop.model.User;

@Service
public class UserService {

    // Khai báo logger
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepo;

    // Constructor để inject UserRepository
    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Tìm kiếm người dùng theo username
    public User findByUsername(String username) {
        try {
            return userRepo.findByUsername(username);
        } catch (Exception e) {
            // Log lỗi khi tìm kiếm người dùng
            logger.error("Error retrieving user by username", e);
            return null;  // Hoặc throw một ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
    }

    public User findByEmail(String email) {
        try {
            return userRepo.findByEmail(email);
        } catch (Exception e) {
            // Log lỗi khi tìm kiếm người dùng
            logger.error("Error retrieving user by username", e);
            return null;  // Hoặc throw một ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
    }

    public User findByPhone(String phone) {
        try {
            return userRepo.findByPhone(phone);
        } catch (Exception e) {
            // Log lỗi khi tìm kiếm người dùng
            logger.error("Error retrieving user by username", e);
            return null;  // Hoặc throw một ngoại lệ tùy thuộc vào yêu cầu của bạn
        }
    }

    public User save(User user) {
        try {
            return userRepo.save(user); // Gọi phương thức save từ UserRepository
        } catch (Exception e) {
            logger.error("Error saving user", e);
            return null;
        }
    }

}

