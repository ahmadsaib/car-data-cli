package com.cardatacli.service;

import com.cardatacli.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CarService implements CarServiceInterface {

    private static final Logger log = LoggerFactory.getLogger(CarService.class);

    private static final Map<String, Comparator<Car>> SORT_OPTIONS = Map.ofEntries(
            Map.entry("year-desc", Comparator.comparing(Car::getReleaseDate).reversed()),
            Map.entry("year-asc", Comparator.comparing(Car::getReleaseDate)),
            Map.entry("price-desc", Comparator.comparing(Car::getPriceUsd).reversed()),
            Map.entry("price-asc", Comparator.comparing(Car::getPriceUsd))
    );

    public List<Car> sortCars(List<Car> cars, String sortOption) {
        if (sortOption == null || sortOption.isBlank()) return cars;

        Comparator<Car> comparator = SORT_OPTIONS.get(sortOption.toLowerCase());
        return comparator != null
                ? cars.stream().sorted(comparator).collect(Collectors.toList())
                : cars;
    }


    public List<Car> filterCars(List<Car> cars, String filter) {
        if (filter == null || filter.isBlank()) return cars;

        String[] filters = filter.split(",");
        boolean hasBrand = false, hasPrice = false, hasReleaseDate = false;

        for (String f : filters) {
            String lower = f.trim().toLowerCase(Locale.ROOT);
            if (lower.startsWith("brand=")) hasBrand = true;
            if (lower.startsWith("price")) hasPrice = true;
            if (lower.startsWith("releaseDate")) hasReleaseDate = true;
        }

        if (!hasBrand && !hasPrice) {
            throw new IllegalArgumentException("Invalid filter combination. Use Brand+Price");
        }
        if (!hasBrand && !hasReleaseDate) {
            throw new IllegalArgumentException("Invalid filter combination. Use Brand+ReleaseDate.");
        }

        for (String f : filters) {
            String lower = f.trim().toLowerCase(Locale.ROOT);

            if (lower.startsWith("brand=")) {
                String brand = f.split("=")[1].trim();
                cars = filterByBrand(cars, brand);
            } else if (lower.startsWith("price")) {
                cars = filterByPrice(cars, lower);
            } else if (lower.startsWith("releaseDate")) {
                cars = filterByDate(cars, lower);
            }
        }

        return cars;
    }


    private static List<Car> filterByBrand(List<Car> cars, String brand) {
        return cars.stream()
                .filter(car -> car.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

    private static List<Car> filterByPrice(List<Car> cars, String condition) {
        try {
            if (condition.contains(">=")) {
                double value = Double.parseDouble(condition.split(">=")[1]);
                return cars.stream().filter(car -> car.getPriceUsd() >= value).collect(Collectors.toList());
            } else if (condition.contains("<=")) {
                double value = Double.parseDouble(condition.split("<=")[1]);
                return cars.stream().filter(car -> car.getPriceUsd() <= value).collect(Collectors.toList());
            } else if (condition.contains(">")) {
                double value = Double.parseDouble(condition.split(">")[1]);
                return cars.stream().filter(car -> car.getPriceUsd() > value).collect(Collectors.toList());
            } else if (condition.contains("<")) {
                double value = Double.parseDouble(condition.split("<")[1]);
                return cars.stream().filter(car -> car.getPriceUsd() < value).collect(Collectors.toList());
            } else if (condition.contains("=")) {
                double value = Double.parseDouble(condition.split("=")[1]);
                return cars.stream().filter(car -> car.getPriceUsd() == value).collect(Collectors.toList());
            }
        } catch (NumberFormatException e) {
            log.error("Invalid price format in filter: {}", condition);
        }
        return cars;
    }

    private static List<Car> filterByDate(List<Car> cars, String condition) {
        try {
            if (condition.contains(">=")) {
                LocalDate date = LocalDate.parse(condition.split(">=")[1].trim());
                return cars.stream().filter(car -> !car.getReleaseDate().isBefore(date)).collect(Collectors.toList());
            } else if (condition.contains("<=")) {
                LocalDate date = LocalDate.parse(condition.split("<=")[1].trim());
                return cars.stream().filter(car -> !car.getReleaseDate().isAfter(date)).collect(Collectors.toList());
            } else if (condition.contains(">")) {
                LocalDate date = LocalDate.parse(condition.split(">")[1].trim());
                return cars.stream().filter(car -> car.getReleaseDate().isAfter(date)).collect(Collectors.toList());
            } else if (condition.contains("<")) {
                LocalDate date = LocalDate.parse(condition.split("<")[1].trim());
                return cars.stream().filter(car -> car.getReleaseDate().isBefore(date)).collect(Collectors.toList());
            } else if (condition.contains("=")) {
                LocalDate date = LocalDate.parse(condition.split("=")[1].trim());
                return cars.stream().filter(car -> car.getReleaseDate().isEqual(date)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("Invalid release date format in filter: {}. Expected format: yyyy-MM-dd", condition);
        }
        return cars;
    }

}