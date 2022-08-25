package com.upskill.rentcars.config;

import com.upskill.rentcars.model.Car;
import com.upskill.rentcars.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CarConfig {

    @Bean
    CommandLineRunner commandLineRunner(CarRepository carRepository){
        return args -> {
            Car car1 = new Car(1L, "Toyota Camry", "Black", 2020, 1111420, "$45/day");
            Car car2 = new Car(2L, "Volkswagen Golf", "Grey", 2012, 1111455, "$30/day");
            Car car3 = new Car(3L, "Lamborghini Urus", "Yellow", 2021, 1111467,"$990/day");
            carRepository.saveAll(
                    List.of(car1, car2, car3)
            );
        };
    }
}
