package com.example.demo.entity;

import lombok.Data;

@Data
public class Employee {
    private Long id;
    private String name;
    private String email;
    private String position;
    private Department department;
}
