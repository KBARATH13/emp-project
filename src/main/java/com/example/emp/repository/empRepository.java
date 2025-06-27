package com.example.emp.repository;

import com.example.emp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface empRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByRoleIgnoreCaseAndGenderIgnoreCase(String role, String gender);
    List<Employee> findByRoleIgnoreCase(String role);
    List<Employee> findByGenderIgnoreCase(String gender);
}