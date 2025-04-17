package com.cardatacli.parser;

import com.cardatacli.model.Car;
import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvCarParser {

    private static final Logger log = LoggerFactory.getLogger(CsvCarParser.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private static final Map<String, String> MODEL_BRAND_MAP = Map.ofEntries(
            Map.entry("RAV4", "Toyota"),
            Map.entry("Civic", "Honda"),
            Map.entry("F-150", "Ford"),
            Map.entry("Model X", "Tesla"),
            Map.entry("330i", "BMW"),
            Map.entry("Q5", "Audi"),
            Map.entry("Silverado", "Chevrolet"),
            Map.entry("C-Class", "Mercedes-Benz"),
            Map.entry("Rogue", "Nissan"),
            Map.entry("Elantra", "Hyundai")
    );

    public static List<Car> parse(File csvFile, List<Car> xmlCars) {
        Map<String, LocalDate> brandToDateMap = extractBrandReleaseDates(csvFile);
        return enrichCarsWithBrandAndDate(xmlCars, brandToDateMap);
    }

    private static Map<String, LocalDate> extractBrandReleaseDates(File csvFile) {
        Map<String, LocalDate> dateMap = new HashMap<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            reader.readNext(); // skip header
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length < 1) continue;

                String[] parts = line[0].split(",");
                if (parts.length != 2) continue;

                String brand = parts[0].trim();
                String dateStr = parts[1].trim();

                try {
                    dateMap.put(brand, LocalDate.parse(dateStr, DATE_FORMAT));
                } catch (Exception e) {
                    log.warn("Invalid date format: '{}' for brand: '{}'", dateStr, brand);
                }
            }
        } catch (Exception e) {
            log.error("Error reading CSV file: {}", e.getMessage(), e);
        }

        return dateMap;
    }

    private static List<Car> enrichCarsWithBrandAndDate(List<Car> cars, Map<String, LocalDate> brandToDateMap) {
        for (Car car : cars) {
            String model = car.getModel();
            if (model == null || model.isBlank()) continue;

            String brand = MODEL_BRAND_MAP.get(model.trim());
            if (brand != null) {
                car.setBrand(brand);

                LocalDate releaseDate = brandToDateMap.get(brand);
                if (releaseDate != null) {
                    car.setReleaseDate(releaseDate);
                }
            }
        }
        return cars;
    }
}
