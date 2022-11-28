package com.upskill.rentcars.service;

import com.upskill.rentcars.model.db.Car;
import com.upskill.rentcars.repository.CarRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    public static final Car CAR_1 = new Car(1L, "BMW X6", "Blue", 2019, "599620",
            "", 66);
    public static final Car CAR_2 = new Car(2L, "Opel Astra", "White", 2015, "985467",
            "", 66);
    public static final Car CAR_3 = new Car(3L, "Tesla", "Pink", 2022, "987234",
            "", 66);
    public static final List<Car> CARS_LIST = Arrays.asList(CAR_1, CAR_2, CAR_3);

    @Mock
    private CarRepository carRepository;
    private CarService carService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
        carService = new CarService(carRepository);
    }

    @Test
    public void whenGetCars_thenRetrievedCorrectValue() {
        Mockito.when(carRepository.findAll()).thenReturn(CARS_LIST);
        List<Car> testCarList = carService.getCars();
        Assert.assertEquals(CARS_LIST, testCarList);
    }

    @Test
    public void whenGetExistingCar_thenRetrievedCorrectValue() {
        long id = 2L;
        Mockito.when(carRepository.findById(id)).thenReturn(Optional.of(CAR_2));
        Car testCar = carService.getCar(id);
        Assert.assertEquals(CAR_2, testCar);
    }

    @Test(expected = RuntimeException.class)
    public void whenGetNonexistentCar_thenExceptionThrows() {
        long id = 5L;
        Mockito.when(carRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        carService.getCar(id);
    }

    @Test
    public void whenAddNewCar_thenCarAdded() {
        Mockito.when(carRepository.findByVinId(CAR_2.getVinId())).thenReturn(null);
        Mockito.when(carRepository.save(CAR_2)).thenReturn(CAR_2);
        Car testCar = carService.addNewCar(CAR_2);
        Assert.assertEquals(CAR_2, testCar);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddCarWithExistingVinId_thenExceptionThrows() {
        Mockito.when(carRepository.findByVinId(CAR_2.getVinId())).thenReturn(CAR_2);
        carService.addNewCar(CAR_2);
    }

    @Test
    public void whenDeleteCar_thenCarDeleted() {
        Mockito.when(carRepository.existsById(CAR_1.getId())).thenReturn(true);
        Assert.assertEquals(true, carService.deleteCar(CAR_1.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenDeleteCarWithNotExistingId_thenExceptionThrows() {
        Mockito.when(carRepository.existsById(6L)).thenReturn(false);
        carService.deleteCar(6L);
    }

    @Test
    public void whenUpdateCar_thenCarUpdated() {
        CarService spyCarService = Mockito.spy(carService);
        Car updatedCar = new Car(1L, "BMW X6", "Blue", 2005, "599620",
                "", 66);
        Mockito.when(carRepository.findById(CAR_1.getId())).thenReturn(Optional.of(CAR_1));
        spyCarService.updateCar(1L, updatedCar);
        Mockito.verify(spyCarService, times(2)).isFieldSet(anyInt());
        Mockito.verify(spyCarService, times(4)).isFieldSet(anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void whenUpdateCarWithNotExistingId_thenExceptionThrows() {
        Mockito.when(carRepository.findById(6L)).thenReturn(Optional.ofNullable(null));
        carService.updateCar(6L, CAR_3);
    }
}
