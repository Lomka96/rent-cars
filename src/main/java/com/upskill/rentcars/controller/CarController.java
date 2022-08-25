package com.upskill.rentcars.controller;

import com.upskill.rentcars.model.Car;
import com.upskill.rentcars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getCars(){
        return carService.getCars();
    }

    @PostMapping
    public void registerNewCar(@RequestBody Car car){
        carService.addNewCar(car);
    }

    @DeleteMapping("/{carId}")
    public void deleteCarById(@PathVariable("carId") Long carId){
        carService.deleteCar(carId);
    }

    /*@PutMapping("/{carId}")
    public void updateCar(@PathVariable("carId") Long carId,
                          @RequestParam(required = false) String costPerDay) {
        carService.updateCar(carId, costPerDay);
    }*/

    @PatchMapping("/{carId}")
    public Car updateCar(@PathVariable("carId") Long carId, @RequestBody Car car) {
        return carService.updateCar(carId, car);
    }
}
