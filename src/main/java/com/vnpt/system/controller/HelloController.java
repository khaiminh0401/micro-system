package com.vnpt.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello World! Spring Boot application is running successfully!";
    }
    
    @GetMapping("/")
    public String home() {
        return "Welcome to VNPT System!";
    }
}
