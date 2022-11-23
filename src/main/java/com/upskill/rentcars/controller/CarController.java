package com.upskill.rentcars.controller;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.service.CarService;
import com.upskill.rentcars.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final OrderService orderService;

    @Autowired
    public CarController(CarService carService, OrderService orderService) {
        this.carService = carService;
        this.orderService = orderService;
    }

    @GetMapping
    public List<Car> getCars(){
        return carService.getCars();
    }

    @GetMapping("/{carId}")
    public Car getCar(@PathVariable("carId") Long carId){
        return carService.getCar(carId);
    }

    @PostMapping
    public void registerNewCar(@RequestBody Car car){
        carService.addNewCar(car);
    }

    @DeleteMapping("/{carId}")
    public void deleteCarById(@PathVariable("carId") Long carId){
        orderService.deleteOrderByCarId(carId);
        carService.deleteCar(carId);
    }

    @PatchMapping("/{carId}")
    public Car updateCar(@PathVariable("carId") Long carId, @RequestBody Car car) {
        return carService.updateCar(carId, car);
    }

    @GetMapping(path = "/images/car/{id}", produces = IMAGE_JPEG_VALUE)
    public byte[] getCarImage(@PathVariable("id") Long carId) throws IOException {
        return Files.readAllBytes(Paths.get(
                System.getProperty("user.dir") + "/src/main/resources/static/images/cars/car" + carId + ".jpg"));
    }
}
