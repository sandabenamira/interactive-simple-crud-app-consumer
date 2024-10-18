package com.example.demo.controller;

import com.example.demo.client.DepartmentClient;
import com.example.demo.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumer/departments")
public class DepartmentController {

    @Autowired
    private DepartmentClient departmentClient;

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentClient.getDepartmentById(id);
    }

    @GetMapping
    public Department[] getAllDepartments() {
        return departmentClient.getAllDepartments();
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentClient.createDepartment(department);
    }

    @GetMapping("/search")
    public Department[] searchDepartmentsByName(@RequestParam("name") String name) {
        return departmentClient.searchDepartmentsByName(name);
    }
}
