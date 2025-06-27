package com.example.emp.controller;

import com.example.emp.model.Employee;
import com.example.emp.service.empService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"https://projectfrontendemp.netlify.app/"})
@RequestMapping("/employees")
public class empController {

    @Autowired
    private empService empService;

    @PostMapping
    public ResponseEntity<Employee> saveEmp(@RequestBody Employee Emp) {
        try {
            Employee savedEmp = empService.saveemp(Emp);
            if (savedEmp == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.ok(savedEmp);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public List<Employee> getAllEmps(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String gender
    ) {
        if (role != null || gender != null) {
            return empService.filteremps(role, gender);
        } else {
            return empService.getAllemps();
        }
    }

    @GetMapping("/{id}")
    public Employee getEmpById(@PathVariable int id) {
        return empService.getempById(id);
    }

    @PutMapping("/{id}")
    public Employee updateEmp(@PathVariable int id, @RequestBody Employee Emp) {
        return empService.updateemp(id, Emp);
    }

    @DeleteMapping("/{id}")
    public void deleteEmp(@PathVariable int id) {
        empService.deleteemp(id);
    }
}