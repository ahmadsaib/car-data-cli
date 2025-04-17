package com.cardatacli;

import com.cardatacli.model.Car;
import com.cardatacli.service.CarService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    private List<Car> mockCars() {
        Car car1 = new Car("SUV", "RAV4", "Toyota", LocalDate.of(2023, 1, 15), 25000.0, Map.of("EUR", 23000.0));
        Car car2 = new Car("Sedan", "Civic", "Honda", LocalDate.of(2022, 6, 10), 22000.0, Map.of("JPY", 3000000.0));
        Car car3 = new Car("Truck", "F-150", "Ford", LocalDate.of(2021, 3, 20), 35000.0, Map.of("USD", 35000.0));

        return List.of(car1, car2, car3);
    }

    @Test
    void testFilterByBrandAndPriceGreaterThan() {
        List<Car> cars = mockCars();
        List<Car> filtered = new CarService().filterCars(cars, "brand=Ford,price>30000");
        assertEquals(1, filtered.size());
        assertEquals("Ford", filtered.get(0).getBrand());
    }

    @Test
    void testFilterByBrandAndReleaseDateAfter() {
        List<Car> cars = mockCars();
        List<Car> filtered = new CarService().filterCars(cars, "brand=Honda,releaseDate>2022-01-01");
        assertEquals(1, filtered.size());
        assertEquals("Honda", filtered.get(0).getBrand());
    }

    @Test
    void testFilterInvalidCombinationThrowsException() {
        List<Car> cars = mockCars();
        assertThrows(IllegalArgumentException.class, () -> {
            new CarService().filterCars(cars, "Price>20000");
        });
    }

    @Test
    void testSortByPriceDescending() {
        List<Car> cars = mockCars();
        List<Car> sorted = new CarService().sortCars(cars, "price-desc");
        assertEquals("Ford", sorted.get(0).getBrand());
    }

    @Test
    void testSortByYearAscending() {
        List<Car> cars = mockCars();
        List<Car> sorted = new CarService().sortCars(cars, "year-asc");
        assertEquals("Ford", sorted.get(0).getBrand());
    }
}