package com.upskill.rentcars;

import com.upskill.rentcars.model.Car;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class RentCarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentCarsApplication.class, args);
    }

    @GetMapping
    public List<Car> greeting(){
        return List.of(
                new Car(1, "Toyota Camry", "Black", 2020, "$45/day"),
                new Car(2, "Volkswagen Golf", "Grey", 2012, "$30/day"),
                new Car(3, "Lamborghini Urus", "Yellow", 2021, "$990/day")
        );
    }
}