package com.example.hbaseSearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@CrossOrigin
public class GetTokenApplication {
    public static void main(String[] args) {
        SpringApplication.run(GetTokenApplication.class, args);
    }
}
