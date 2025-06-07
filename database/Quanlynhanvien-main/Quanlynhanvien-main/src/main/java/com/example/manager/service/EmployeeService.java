package com.example.manager.service;

import com.example.manager.entity.Employee;
import com.example.manager.entity.Salary;
import com.example.manager.entity.User;
import com.example.manager.repository.EmployeeRepository;
import com.example.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public double tinhLuong(Salary salary) {
        double luongNgay = salary.getLuongcoban();
        double phatNgayNghi = luongNgay * 0.5;
        double tong = ((luongNgay * salary.getNgaylam()) - (salary.getNgaynghi() * phatNgayNghi)) * salary.getHesoluong();
        if (tong < 0) tong = 0;
        double thuePhanTram = salary.getThue();
        tong = tong - (tong * thuePhanTram);
        tong = tong * salary.getHesoluong();
        return tong;
    }
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee saveEmployeeWithUser(Employee employee, String username) {
        // Mật khẩu mặc định "123456" mã hóa
        String defaultPassword = passwordEncoder.encode("123456");

        User user = new User(username, defaultPassword);

        // Liên kết 2 chiều
        employee.setUser(user);
        user.setEmployee(employee);
        userRepository.save(user);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteById(long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            User user = employee.getUser();
            if (user != null) {
                // Gỡ liên kết hai chiều trước khi xoá
                employee.setUser(null);
                user.setEmployee(null);
                userRepository.delete(user);
            }
            employeeRepository.delete(employee);
        }
    }
}
