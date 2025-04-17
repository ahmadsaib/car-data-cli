package com.cardatacli.controller;

import com.cardatacli.model.Car;
import com.cardatacli.parser.CsvCarParser;
import com.cardatacli.parser.XmlCarParser;
import com.cardatacli.service.CarServiceInterface;
import com.cardatacli.util.OutputFormatter;
import org.slf4j.Logger;

import java.io.File;
import java.util.List;

public class CarDataController {

    private final Logger logger;
    private final CarServiceInterface carService;

    public CarDataController(Logger logger, CarServiceInterface carService) {
        this.logger = logger;
        this.carService = carService;
    }

    public void run(String csvPath, String xmlPath, String filter, String sort, String output) {
        try {
            File csvFile = new File(csvPath);
            File xmlFile = new File(xmlPath);

            if (!csvFile.exists() || !xmlFile.exists()) {
                throw new IllegalArgumentException("CSV or XML file does not exist.");
            }

            logger.info("Parsing XML and CSV files...");
            List<Car> cars = XmlCarParser.parse(xmlFile);
            cars = CsvCarParser.parse(csvFile, cars);

            logger.info("Applying filter and sorting...");
            cars = carService.filterCars(cars, filter);
            cars = carService.sortCars(cars, sort);

            logger.info("Outputting data in format: {}", output);
            OutputFormatter.format(cars, output);

        } catch (Exception e) {
            logger.error("Error while running CarDataController: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}

