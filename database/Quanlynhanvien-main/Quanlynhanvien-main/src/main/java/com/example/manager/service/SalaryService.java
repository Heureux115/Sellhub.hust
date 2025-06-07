package com.example.manager.service;

import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaryService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryRepository salaryRepository;

    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public String addSalary(Long employeeId) {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);
        Employee employee = optEmp.get();
        Salary salary = salaryRepository.findByEmployee(employee)
                .orElseGet(() -> {
                    Salary s = new Salary();
                    s.setEmployee(employee);
                    s.setNgaylam(0);
                    s.setNgaynghi(0);
                    s.setLuongcoban(100000);
                    s.setThue(0.05);
                    s.setHesoluong(1);
                    return s;
                });

        salaryRepository.save(salary);

        return "SUCCESS";
    }
    public Salary saveSalary(Salary salary) {
        return salaryRepository.save(salary);
    }
}
