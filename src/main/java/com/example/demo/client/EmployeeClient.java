package com.example.demo.client;

import com.example.demo.entity.Employee;
import com.example.demo.entity.PaginatedResponse;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeClient {

    private final RestTemplate restTemplate;

    public EmployeeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Employee getEmployeeById(Long id) {
        String url = "http://localhost:8080/api/employees/" + id;
        try {
            return restTemplate.getForObject(url, Employee.class);
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                // Handle 404 Not Found
                throw new RuntimeException("Employee not found: " + id);
            } else {
                throw new RuntimeException("Error fetching employee: " + ex.getMessage());
            }
        }
    }

    public Employee[] getAllEmployees() {
        String url = "http://localhost:8080/api/employees";
        try {
            return restTemplate.getForObject(url, Employee[].class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error fetching employees: " + ex.getMessage());
        }
    }

    public Employee createEmployee(Employee employee) {
        String url = "http://localhost:8080/api/employees";
        try {
            return restTemplate.postForObject(url, employee, Employee.class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error creating employee: " + ex.getMessage());
        }
    }

    public PaginatedResponse<Employee> getEmployeesPaginated(int page, int size) {
        String url = "http://localhost:8080/api/employees/paginated?page=" + page + "&size=" + size;
        try {
            ResponseEntity<PaginatedResponse<Employee>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PaginatedResponse<Employee>>() {}
            );
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error fetching paginated employees: " + ex.getMessage());
        }
    }

    // Search method with error handling
    public Employee[] searchEmployeesByName(String name) {
        String url = "http://localhost:8080/api/employees/search?name=" + name;
        try {
            return restTemplate.getForObject(url, Employee[].class);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("Error searching for employees with name '" + name + "': " + ex.getMessage());
        }
    }

    
}
