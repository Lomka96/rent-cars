package com.upskill.rentcars.service;

import com.upskill.rentcars.model.Car;
import com.upskill.rentcars.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars(){
        return carRepository.findAll();
    }

    public void addNewCar(Car car) {
        Optional<Car> carByVinId = carRepository.findCarByVinId(car.getVinId());
        if(carByVinId.isPresent()) {
            throw new IllegalArgumentException("Vin taken");
        }
        carRepository.save(car);
    }

    public void deleteCar(Long carId) {
        boolean exists = carRepository.existsById(carId);
        if(!exists){
            throw new IllegalArgumentException("Car with id " + carId + " does not exists");
        }
        carRepository.deleteById(carId);
    }

    /*@Transactional
    public void updateCar(Long carId, String costPerDay){
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new IllegalStateException(
                        "car with id " + carId + " does not exists"
                ));
        if(costPerDay != null && costPerDay.length() > 0 && !Objects.equals(car.getCostPerDay(), costPerDay)){
            car.setCostPerDay(costPerDay);
        }
    }*/

    @Transactional
    public Car updateCar(Long carId, Car updateCar) {
        Car car = carRepository.findById(carId).orElseThrow(() ->
                new IllegalStateException("car with id " + carId + " does not exists"));

        if (isFieldSet(updateCar.getCostPerDay())) {
            car.setCostPerDay(updateCar.getCostPerDay());
        }
        if (isFieldSet(updateCar.getColor())) {
            car.setColor(updateCar.getColor());
        }
        if (isFieldSet(updateCar.getModel())) {
            car.setModel(updateCar.getModel());
        }
        if (isField(updateCar.getYear())) {
            car.setYear(updateCar.getYear());
        }
        if (isField(updateCar.getVinId())) {
            car.setVinId(updateCar.getVinId());
        }
        return carRepository.save(car);
    }

    public boolean isFieldSet(String field) {
        return !(field == null || field.isEmpty());
    }

    public boolean isField(int field) {
        return (field != 0);
    }
}
