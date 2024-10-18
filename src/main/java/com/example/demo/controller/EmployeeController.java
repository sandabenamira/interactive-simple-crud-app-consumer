package com.example.demo.controller;

import com.example.demo.client.EmployeeClient;
import com.example.demo.entity.Employee;
import com.example.demo.entity.PaginatedResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumer/employees")
public class EmployeeController {

    @Autowired
    private EmployeeClient employeeClient;

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeClient.getEmployeeById(id);
    }

    @GetMapping
    public Employee[] getAllEmployees() {
        return employeeClient.getAllEmployees();
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeClient.createEmployee(employee);
    }

     @GetMapping("/paginated")
    public PaginatedResponse<Employee> getEmployeesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return employeeClient.getEmployeesPaginated(page, size);
    }

    @GetMapping("/search")
    public Employee[] searchEmployeesByName(@RequestParam("name") String name) {
        return employeeClient.searchEmployeesByName(name);
    }
}
