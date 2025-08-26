package com.example.emp.service;
import com.example.emp.model.Employee;
import com.example.emp.model.User;
import com.example.emp.repository.empRepository;
import com.example.emp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class empService {
    @Autowired
    private empRepository empRepository;

    @Autowired
    private UserRepository userRepository;

    private String getLoggedInUserCompanyName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCompanyName();
    }

    public Employee saveemp(Employee Emp) {
        Emp.setCompanyName(getLoggedInUserCompanyName());
        return empRepository.save(Emp);
    }

    public List<Employee> getAllemps() {
        return empRepository.findByCompanyName(getLoggedInUserCompanyName());
    }

    public Employee getempById(int id) {
        Employee employee = empRepository.findById(id).orElse(null);
        if (employee != null && employee.getCompanyName().equals(getLoggedInUserCompanyName())) {
            return employee;
        }
        return null;
    }

    public Employee updateemp(int id, Employee Emp) {
        Employee existing = empRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found with id:  + id"));
        if (existing != null && existing.getCompanyName().equals(getLoggedInUserCompanyName())) {
            existing.setName(Emp.getName());
            existing.setAge(Emp.getAge());
            existing.setGender(Emp.getGender());
            existing.setEmail(Emp.getEmail());
            existing.setRole(Emp.getRole());
            return empRepository.save(existing);
        }
        return null;
    }

    public void deleteemp(int id) {
        Employee employee = empRepository.findById(id).orElse(null);
        if (employee != null && employee.getCompanyName().equals(getLoggedInUserCompanyName())) {
            empRepository.deleteById(id);
        }
    }

    public List<Employee> filteremps(String role, String gender) {
        String companyName = getLoggedInUserCompanyName();
        if (role != null && !role.isEmpty() && gender != null && !gender.isEmpty()) {
            return empRepository.findByCompanyNameAndRoleIgnoreCaseAndGenderIgnoreCase(companyName, role, gender);
        } else if (role != null && !role.isEmpty()) {
            return empRepository.findByCompanyNameAndRoleIgnoreCase(companyName, role);
        } else if (gender != null && !gender.isEmpty()) {
            return empRepository.findByCompanyNameAndGenderIgnoreCase(companyName, gender);
        } else {
            return empRepository.findByCompanyName(companyName);
        }
    }
}