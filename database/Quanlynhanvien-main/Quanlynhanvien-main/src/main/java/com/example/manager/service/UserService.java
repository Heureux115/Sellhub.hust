package com.example.manager.service;

import com.example.manager.entity.User;
import com.example.manager.entity.Employee;
import com.example.manager.repository.UserRepository;
import com.example.manager.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void createUser(String username, String rawPassword, Long employeeId) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        if (employeeId != null) {
            Employee employee = employeeRepository.findById(employeeId).orElse(null);
            if (employee != null) {
                user.setEmployee(employee);
                employee.setUser(user);
            }
        }

        userRepository.save(user);
    }
}
