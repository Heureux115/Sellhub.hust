package com.example.shop.controller;

import com.example.shop.model.User;
import com.example.shop.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            return "redirect:/home"; // hoặc return "redirect:/home";
        } else {
            model.addAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
            return "login"; // phải forward về login, không redirect
        }
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String password,
                           @RequestParam String confirmpassword,
                           Model model) {
        if (!StringUtils.hasText(username) ||
                !StringUtils.hasText(email) ||
                !StringUtils.hasText(phone) ||
                !StringUtils.hasText(password) ||
                !StringUtils.hasText(confirmpassword)) {
            model.addAttribute("error", "Vui lòng điền đầy đủ tất cả các trường");
            return "register";
        }
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Tên đăng nhập đã tồn tại");
            return "register";
        } else if (userService.findByEmail(email) != null) {
            model.addAttribute("error", "Email đã tồn tại");
            return "register";
        }else if (userService.findByPhone(phone) != null) {
            model.addAttribute("error", "Số điện thoại đã tồn tại");
            return "register";
        } else if (!password.equals(confirmpassword)) {
            model.addAttribute("error", "Mật khẩu không trùng nhau");
            return "register";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        userService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}

