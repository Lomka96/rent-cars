package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.model.db.Customer;
import com.upskill.rentcars.repository.CarRepository;
import com.upskill.rentcars.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CarService implements com.upskill.rentcars.service.Service {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<Car> getCars(){
        return carRepository.findAll();
    }

    @Override
    public Car getCar(Long carId) {
        log.info("Fetching car by id: {}", carId);
        return carRepository.findById(carId).get();
    }

    @Override
    public Car addNewCar(Car car) {
        Optional<Car> carByVinId = carRepository.findByVinId(car.getVinId());
        if(carByVinId.isPresent()) {
            throw new IllegalArgumentException("Vin taken");
        }
        //car.setImageUrl(setServerImageUrl());
        log.info("Saving a new car with vinId: {}", car.getVinId());
        return carRepository.save(car);
    }

    @Override
    public boolean deleteCar(Long carId) {
        boolean exists = carRepository.existsById(carId);
        if(!exists){
            throw new IllegalArgumentException("Car with id " + carId + " does not exists");
        }
        log.info("Deleting a car with id: {}", carId);
        carRepository.deleteById(carId);
        return true;
    }

    @Transactional
    @Override
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
        if (isFieldSet(updateCar.getYear())) {
            car.setYear(updateCar.getYear());
        }
        if (isFieldSet(updateCar.getVinId())) {
            car.setVinId(updateCar.getVinId());
        }
        if (isFieldSet(updateCar.getImageUrl())) {
            car.setImageUrl(updateCar.getImageUrl());
        }
        log.info("Updating a car with vinId: {}", car.getVinId());
        return carRepository.save(car);
    }

    @Override
    public List<Car> list(int limit) {
        log.info("Fetching all cars");
        return carRepository.findAll(PageRequest.of(0, limit)).toList();
    }

    /*@Override
    public Car addCustomerToList(Long carId, Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("customer with id " + id + " does not exists"));
        Car car = carRepository.findById(carId).orElseThrow(() ->
                new IllegalStateException("car with carId " + carId + " does not exists"));
        //car.customerList.add(customer);
        return carRepository.save(car);
    }*/

    public boolean isFieldSet(String field) {
        return !(field == null || field.isEmpty());
    }

    public boolean isFieldSet(int field) {
        return (field != 0);
    }

    /*private String setServerImageUrl(){
        Random random = new Random();
        int pictureNumber = random.nextInt(10);
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(
                "/cars/images/car/" + pictureNumber).toUriString();
    }*/

    private String setServerImageUrl(){
        String[] imageNames = {"car1.png", "car2.png", "car3.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/cars/image" + imageNames[2]).toUriString();
    }

    public Optional<Car> findCarById(Long carId){
        return carRepository.findById(carId);
    }
}
