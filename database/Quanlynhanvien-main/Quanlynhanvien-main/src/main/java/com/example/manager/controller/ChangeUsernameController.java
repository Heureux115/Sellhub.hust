package com.example.manager.controller;

import com.example.manager.entity.User;
import com.example.manager.repository.UserRepository;
import com.example.manager.service.EmployeeUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class ChangeUsernameController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/change-username")
    public String showChangeUsernameForm(Model model) {
        model.addAttribute("newUsername", "");
        return "change_username";
    }


    @PostMapping("/change-username")
    public String changePassword(@RequestParam("newUsername") String newUsername,
                                 Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        EmployeeUserDetails userDetails = (EmployeeUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        user.setUsername(newUsername);
        userRepository.save(user);

        model.addAttribute("message", "Đổi tên đăng nhập thành công.");
        return "change_username";
    }
}


