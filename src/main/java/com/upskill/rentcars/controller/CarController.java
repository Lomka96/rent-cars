package com.upskill.rentcars.controller;

import com.upskill.rentcars.model.Car;
import com.upskill.rentcars.model.Response;
import com.upskill.rentcars.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/list")
    public List<Car> getCars(){
        return carService.getCars();
    }

    @GetMapping("/{carId}")
    public Car getCar(@PathVariable("carId") Long carId){
        return carService.getCar(carId);
    }

    @PostMapping("/save")
    public void registerNewCar(@RequestBody Car car){
        carService.addNewCar(car);
    }

    @DeleteMapping("/delete/{carId}")
    public void deleteCarById(@PathVariable("carId") Long carId){
        carService.deleteCar(carId);
    }

    @PatchMapping("/update/{carId}")
    public Car updateCar(@PathVariable("carId") Long carId, @RequestBody Car car) {
        return carService.updateCar(carId, car);
    }

/*    @GetMapping("/list")
    public ResponseEntity<Response> getCars() throws InterruptedException {
        //TimeUnit.SECONDS.sleep(3);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("cars", carService.list(10)))
                        .message("cars retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }*/

    /*@GetMapping("/get/{id}")
    public ResponseEntity<Response> getCar(@PathVariable("id") Long carId){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("car", carService.getCar(carId)))
                        .message("car retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }*/

    /*@PostMapping("/save")
    public ResponseEntity<Response> saveCar(@RequestBody @Valid Car car){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("car", carService.addNewCar(car)))
                        .message("car saved")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }*/

    /*@PatchMapping("/update/{id}")
    public ResponseEntity<Response> updateCard(@PathVariable("id") Long carId, @RequestBody @Valid Car car) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .statusCode(HttpStatus.OK.value())
                        .status(HttpStatus.OK)
                        .message("Card updated")
                        .data(Map.of("car", carService.updateCar(carId, car)))
                        .build()
        );
    }*/

    /*@DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteCar(@PathVariable("id") Long carId){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(Map.of("deleted", carService.deleteCar(carId)))
                        .message("car deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }*/
    /*@GetMapping(path = "/images/car/{id}", produces = IMAGE_JPEG_VALUE)

    public byte[] getCarImage(@PathVariable("id") Long carId) throws IOException {
        Random random = new Random();
        carId = Long.valueOf(random.nextInt(10));
        return Files.readAllBytes(Paths.get(
                System.getProperty("user.dir") + "/src/main/resources/static/images/cars/car" + carId + ".jpg"));
    }*/

    @GetMapping(path = "/images/car/{id}", produces = IMAGE_JPEG_VALUE)
    public byte[] getCarImage(@PathVariable("id") Long carId) throws IOException {
        return Files.readAllBytes(Paths.get(
                System.getProperty("user.dir") + "/src/main/resources/static/images/cars/car" + carId + ".jpg"));
    }
}
