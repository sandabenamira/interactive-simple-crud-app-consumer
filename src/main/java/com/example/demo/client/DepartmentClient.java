package com.example.demo.client;

import com.example.demo.entity.Department;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class DepartmentClient {

    private final RestTemplate restTemplate;

    public DepartmentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Department getDepartmentById(Long id) {
        String url = "http://localhost:8080/api/departments/" + id;
        try {
            return restTemplate.getForObject(url, Department.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Handle 404 Not Found
                throw new RuntimeException("Department not found: " + id);
            } else {
                throw new RuntimeException("Error fetching department: " + ex.getMessage());
            }
        }
    }

    public Department[] getAllDepartments() {
        String url = "http://localhost:8080/api/departments";
        try {
            return restTemplate.getForObject(url, Department[].class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error fetching departments: " + ex.getMessage());
        }
    }

    public Department createDepartment(Department department) {
        String url = "http://localhost:8080/api/departments";
        try {
            return restTemplate.postForObject(url, department, Department.class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error creating department: " + ex.getMessage());
        }
    }

    public Department[] searchDepartmentsByName(String name) {
        String url = "http://localhost:8080/api/departments/search?name=" + name;
        try {
            return restTemplate.getForObject(url, Department[].class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error searching for departments with name '" + name + "': " + ex.getMessage());
        }
    }
}
