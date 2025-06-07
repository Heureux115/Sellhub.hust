package com.example.manager.controller;

import com.example.manager.entity.User;
import com.example.manager.repository.UserRepository;
import com.example.manager.service.EmployeeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/password")
public class ChangePasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/change")
    public String showChangePasswordForm() {
        return "change_password";
    }

    @PostMapping("/change")
    public String changePassword(@RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeUserDetails userDetails = (EmployeeUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            model.addAttribute("error", "Mật khẩu cũ không đúng.");
            return "change_password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
            return "change_password";
        }

        // Cập nhật mật khẩu mới đã mã hóa
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        model.addAttribute("message", "Đổi mật khẩu thành công.");
        return "change_password";
    }
}