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
    public String addEmployee(@ModelAttribute Employee employee,
                              @RequestParam String username,
                              @RequestParam("hesoluong") double hesoluong,
                              @RequestParam("thue") double thue,
                              Model model) {
        Employee savedEmployee = employeeService.saveEmployeeWithUser(employee, username);

        Salary salary = new Salary();
        salary.setEmployee(savedEmployee);
        salary.setHesoluong(hesoluong);
        salary.setThue(thue);

        salary.setNgaylam(0);
        salary.setNgaynghi(0);
        salary.setLuongcoban(0);

        salaryService.saveSalary(salary);

        model.addAttribute("message", "Thêm nhân viên thành công");
        model.addAttribute("employee", new Employee());
        return "employee_add";
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