package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.repository.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    private AutoCloseable autoCloseable;
    private CarService underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CarService(carRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetCars() {
        underTest.getCars();
        verify(carRepository).findAll();
    }

    @Test
    void addNewCar() {
        Car car = new Car(
                1L,
                "BMW X6",
                "Blue",
                2019,
                "599620",
                "",
                66
        );

        underTest.addNewCar(car);

        ArgumentCaptor<Car> argumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).save(argumentCaptor.capture());
        Car capturedCar = argumentCaptor.getValue();
        assertThat(capturedCar).isEqualTo(car);
    }

    @Test
    void willThrowWhenVinIsTaken() {
        Car car = new Car(
                1L,
                "BMW X6",
                "Blue",
                2019,
                "599620",
                "",
                66
        );

        given(carRepository.findByVinId(car.getVinId()))
                .willReturn(true);

        assertThatThrownBy(() -> underTest.addNewCar(car))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Vin taken");

        verify(carRepository, never()).save(any());
    }

    @Test
    void deleteCar() {
        Car car = new Car(
                1L,
                "BMW X6",
                "Blue",
                2019,
                "599620",
                "",
                66
        );
        underTest.addNewCar(car);
        ArgumentCaptor<Car> argumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).delete(argumentCaptor.capture());
        carRepository.delete(argumentCaptor.capture());
        //underTest.deleteCar(carRepository.findById(1L).get().getId());
//        Car car1 = null;
//        if(underTest.findCarById(car.getId()).isPresent()){
//            car1 = carRepository.findById(car.getId()).get();
//        }
//        assertThat(car1).isNull();
        Car capturedCar = argumentCaptor.getValue();
        assertThat(capturedCar).isNull();
    }

    @Test
    void updateCar() {
        Car car = new Car(
                1L,
                "BMW X6",
                "Blue",
                2019,
                "599620",
                "",
                66
        );

        underTest.addNewCar(car);
        ArgumentCaptor<Car> argumentCaptor = ArgumentCaptor.forClass(Car.class);
        verify(carRepository).save(argumentCaptor.capture());

        car.setVinId("111122");
        underTest.updateCar(1L, car);
        
        verify(carRepository).save(argumentCaptor.capture());
        Car capturedCar = argumentCaptor.getValue();
        assertThat(capturedCar).isEqualTo(car);
    }

    @Test
    void findCarById() {
    }
}