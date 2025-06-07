package com.example.manager.controller;



import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.service.EmployeeService;
import com.example.manager.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SalaryService salaryService;


    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee_add";
    }

    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee employee,
                              @ModelAttribute Salary salary,
                              @RequestParam String username,
                              Model model) {
        employeeService.saveEmployeeWithUser(employee, username);
        salaryService.addSalary(employee.getId());
        model.addAttribute("message", "Thêm nhân viên thành công");
        model.addAttribute("employee", new Employee()); // reset form
        return "employee_add";
    }
    @GetMapping("/update/salary")
    public String showUpdateForm() {
        return "salary_update";
    }

    @PostMapping("/update/salary")
    public String updateSalary(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("ngaylam") int ngaylam,
            @RequestParam("ngaynghi") int ngaynghi,
            @RequestParam("hesoluong") double hesoluong,
            @RequestParam("thue") double thue,
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            Model model) {

        // Tìm nhân viên theo ID
        Optional<Employee> empOpt = salaryService.findEmployeeById(employeeId);
        if (empOpt.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy nhân viên với ID " + employeeId);
            return "salary_update";
        }

        Employee employee = empOpt.get();

        Salary salary = salaryService.findByEmployeeAndMonthYear(employee, month, year)
                .orElse(new Salary());

        salary.setEmployee(employee);
        salary.setNgaylam(ngaylam);
        salary.setNgaynghi(ngaynghi);
        salary.setHesoluong(hesoluong);
        salary.setThue(thue);
        salary.setMonth(month);
        salary.setYear(year);

        salary.setLuongcoban(0);

        salaryService.saveSalary(salary);

        model.addAttribute("message", "Cập nhật bảng lương thành công cho nhân viên " + employee.getName());
        return "salary_update";
    }


    @GetMapping("/employeeName")
    @ResponseBody
    public Map<String, Object> getEmployeeName(@RequestParam Long employeeId) {
        Optional<Employee> empOpt = salaryService.findEmployeeById(employeeId);
        Map<String, Object> result = new HashMap<>();
        if (empOpt.isPresent()) {
            result.put("exists", true);
            result.put("name", empOpt.get().getName());
        } else {
            result.put("exists", false);
        }
        return result;
    }

}