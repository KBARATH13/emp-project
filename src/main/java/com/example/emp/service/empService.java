package com.example.emp.service;
import com.example.emp.model.Employee;
import com.example.emp.repository.empRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class empService {
    @Autowired
    private empRepository empRepository;

    public Employee saveemp(Employee Emp) {
        return empRepository.save(Emp);
    }

    public List<Employee> getAllemps() {
        return empRepository.findAll();
    }

    public Employee getempById(int id) {
        return empRepository.findById(id).orElse(null);
    }

    public Employee updateemp(int id, Employee Emp) {
        Employee existing = empRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found with id:  + id"));
        existing.setName(Emp.getName());
        existing.setAge(Emp.getAge());
        existing.setGender(Emp.getGender());
        existing.setEmail(Emp.getEmail());
        existing.setRole(Emp.getRole());
        return empRepository.save(existing);
    }

    public void deleteemp(int id) {
        empRepository.deleteById(id);
    }

    public List<Employee> filteremps(String role, String gender) {
        if (role != null && !role.isEmpty() && gender != null && !gender.isEmpty()) {
            return empRepository.findByRoleIgnoreCaseAndGenderIgnoreCase(role, gender);
        } else if (role != null && !role.isEmpty()) {
            return empRepository.findByRoleIgnoreCase(role);
        } else if (gender != null && !gender.isEmpty()) {
            return empRepository.findByGenderIgnoreCase(gender);
        } else {
            return empRepository.findAll();
        }
    }
}