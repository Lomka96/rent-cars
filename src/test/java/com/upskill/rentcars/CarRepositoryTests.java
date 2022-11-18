package com.upskill.rentcars;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarRepositoryTests {

    @Autowired
    private CarRepository carRepository;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void saveCarTest(){

        Car car = Car.builder()

                .model("BMW X6")
                .color("Blue")
                .year(2019)
                .vinId("599620")
                .imageUrl("")
                .costPerDay(66)
                .build();

        carRepository.save(car);

        Assertions.assertThat(car.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getCarTest(){
        Car car = carRepository.findById(1L).get();

        Assertions.assertThat(car.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getListOfCarsTest(){

        List<Car> cars = carRepository.findAll();

        Assertions.assertThat(cars.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateCarTest(){
        Car car = carRepository.findById(1L).get();
        car.setVinId("155155");
        Car carUpdated = carRepository.save(car);

        Assertions.assertThat(carUpdated.getVinId()).isEqualTo("155155");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteCarTest(){
        Car car = carRepository.findById(1L).get();
        carRepository.delete(car);
        Car car1 = null;
        Optional<Car> optionalCar = carRepository.findById(1L);
        if(optionalCar.isPresent()){
            car1 = optionalCar.get();
        }

        Assertions.assertThat(car1).isNull();
    }
}
