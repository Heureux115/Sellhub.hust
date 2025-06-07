package com.example.manager.controller;

import com.example.manager.entity.Employee;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.repository.UserRepository;
import com.example.manager.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/view")
    public String showAllEmployees(Model model, HttpServletRequest request) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        boolean isManager = request.isUserInRole("QUANLY");
        model.addAttribute("isManager", isManager);
        return "employee_view";
    }
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        employeeService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa nhân viên thành công!");
        return "redirect:/view";
    }
}
