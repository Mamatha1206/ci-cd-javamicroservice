package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class YourApp {

    public static void main(String[] args) {
        System.setProperty("server.port", "8888"); // Optional if using application.properties
        SpringApplication.run(YourApp.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello from Java App on port 8888!";
    }
}
