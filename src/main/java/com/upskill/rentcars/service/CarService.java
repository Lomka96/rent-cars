package com.upskill.rentcars.service;

import com.upskill.rentcars.model.Car;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    public List<Car> getCars(){
        return List.of(
                new Car(1, "Toyota Camry", "Black", 2020, "$45/day"),
                new Car(2, "Volkswagen Golf", "Grey", 2012, "$30/day"),
                new Car(3, "Lamborghini Urus", "Yellow", 2021, "$990/day")
        );
    }
}
