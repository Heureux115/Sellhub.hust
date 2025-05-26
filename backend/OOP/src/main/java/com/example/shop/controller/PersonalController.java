package com.example.shop.controller;

import com.example.shop.model.*;
import com.example.shop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonalController {
    private final UserService userService;

    public PersonalController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/personal")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "personal-acc";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmNewPassword,
                                 HttpSession session,
                                 Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // chưa đăng nhập
        }

        if (!user.getPassword().equals(currentPassword)) {
            model.addAttribute("error", "Mật khẩu hiện tại không đúng");
            return "change-password";
        }

        if (!newPassword.equals(confirmNewPassword)) {
            model.addAttribute("error", "Mật khẩu mới không trùng khớp");
            return "change-password";
        }

        user.setPassword(newPassword);
        userService.save(user); // cập nhật lại user

        model.addAttribute("message", "Đổi mật khẩu thành công");
        return "home";
    }

    @PostMapping("/change-email")
    public String changeEmail(@RequestParam String newEmail,
                              @RequestParam String oldEmail,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (!user.getEmail().equals(oldEmail)) {
            model.addAttribute("error", "Email hiện tại không đúng");
            return "change-email";
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Mật khẩu không đúng");
            return "change-email";
        }

        if (newEmail == null || newEmail.isEmpty()) {
            model.addAttribute("error", "Email mới không được để trống");
            return "change-email";
        }

        user.setEmail(newEmail);
        userService.save(user);

        model.addAttribute("message", "Cập nhật email thành công");
        return "personal-acc";
    }

    @PostMapping("/change-phone")
    public String changePhone(@RequestParam String newPhone,
                              @RequestParam String oldPhone,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (!user.getPhone().equals(oldPhone)) {
            model.addAttribute("error", "Số điện thoại hiện tại không đúng");
            return "change-phone";
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute("error", "Mật khẩu không đúng");
            return "change-phone";
        }

        if (newPhone == null || newPhone.isEmpty()) {
            model.addAttribute("error", "Số điện thoại mới không được để trống");
            return "change-phone";
        }

        user.setPhone(newPhone);
        userService.save(user);

        model.addAttribute("message", "Cập nhật số điện thoại thành công");
        return "personal-acc";
    }

}
