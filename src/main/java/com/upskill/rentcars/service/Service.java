package com.upskill.rentcars.service;

import com.upskill.rentcars.model.Car;

import java.util.List;

public interface Service {

    List<Car> getCars();

    Car getCar(Long carId);

    Car addNewCar(Car car);

    boolean deleteCar(Long carId);

    Car updateCar(Long carId, Car updateCar);

    List<Car> list(int limit);
}