package com.upskill.rentcars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RentCarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentCarsApplication.class, args);
    }

    @GetMapping
    public String greeting(){
        return "Welcome to Rent Cars!";
    }
}