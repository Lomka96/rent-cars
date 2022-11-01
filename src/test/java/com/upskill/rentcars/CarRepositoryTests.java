package com.upskill.rentcars;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CarRepositoryTests {

    @Autowired
    private CarRepository carRepository;

    @Test
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
}
