package com.example.manager.controller;

import com.example.manager.entity.Salary;
import com.example.manager.repository.SalaryRepository;
import com.example.manager.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/view")
    public String viewSalary(@RequestParam(value = "month", required = false) Integer month,
                             @RequestParam(value = "year", required = false) Integer year,
                             Model model) {

        if (month == null) month = 1;
        if (year == null) year = 2025;

        List<Salary> salaries = salaryRepository.findByMonthAndYear(month, year);
        Map<Long, Double> luongMap = new HashMap<>();

        for (Salary s : salaries) {
            double luong = employeeService.tinhLuong(s);
            luongMap.put(s.getId(), luong);
        }

        model.addAttribute("salaries", salaries);
        model.addAttribute("luongMap", luongMap);
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        return "salary_view";
    }

}
