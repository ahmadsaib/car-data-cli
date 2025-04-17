package com.cardatacli;

import com.cardatacli.controller.CarDataController;
import com.cardatacli.service.CarService;
import com.cardatacli.service.CarServiceInterface;
import com.cardatacli.util.AppLogger;
import org.apache.commons.cli.*;

import org.slf4j.Logger;

public class Main {

    public static void main(String[] args) {
        Logger logger = AppLogger.getLogger();
        Options options = new Options();

        options.addOption("csv", true, "CSV file path");
        options.addOption("xml", true, "XML file path");
        options.addOption("filter", true, "Filter: Brand=value or Price>value");
        options.addOption("sort", true, "Sort by: year-desc or price-desc");
        options.addOption("output", true, "Output format: json, table, xml");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);
            String csvPath = cmd.getOptionValue("csv");
            String xmlPath = cmd.getOptionValue("xml");
            String filter = cmd.getOptionValue("filter");
            String sort = cmd.getOptionValue("sort", "year-desc");
            String output = cmd.getOptionValue("output", "table");

            if (csvPath == null || xmlPath == null) {
                throw new IllegalArgumentException("Both --csv and --xml file paths must be provided.");
            }

            CarServiceInterface carService = new CarService(); // instantiate the implementation
            new CarDataController(logger, carService).run(csvPath, xmlPath, filter, sort, output);
        } catch (ParseException e) {
            logger.error("Failed to parse command line arguments: {}", e.getMessage());
            formatter.printHelp("car-data-cli", options);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage(), e);
        }
    }
}