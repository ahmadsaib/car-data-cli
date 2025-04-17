package com.cardatacli.util;

import com.cardatacli.model.Car;
import com.cardatacli.model.CarList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutputFormatter {

    private static final Logger log = LoggerFactory.getLogger(OutputFormatter.class);

    public static void format(List<Car> cars, String format) {
        switch (format.toLowerCase()) {
            case "json" -> printJson(cars);
            case "xml" -> printXml(cars);
            case "table" -> printTable(cars);
            default -> {
                log.warn("Unsupported format '{}'. Falling back to table.", format);
                printTable(cars);
            }
        }
    }

    private static void printJson(List<Car> cars) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(cars);
            System.out.println(json);
        } catch (Exception e) {
            log.error("Failed to convert to JSON", e);
        }
    }

    private static void printXml(List<Car> cars) {
        try {
            CarList wrapper = new CarList();
            wrapper.setCars(cars);
            JAXBContext context = JAXBContext.newInstance(CarList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(wrapper, System.out);
        } catch (Exception e) {
            log.error("Failed to convert to XML", e);
        }
    }

    private static void printTable(List<Car> cars) {
        // Header
        System.out.printf("%-20s %-15s %-10s %-12s %-12s %-12s %-12s %-12s%n",
                "Brand", "Model", "Type", "ReleaseDate", "USD", "EUR", "JPY", "GBP");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (Car car : cars) {
            String brand = nullSafe(car.getBrand());
            String model = nullSafe(car.getModel());
            String type = nullSafe(car.getType());
            String releaseDate = car.getReleaseDate() != null ? car.getReleaseDate().toString() : "N/A";

            Map<String, Double> prices = car.getPrices() != null ? car.getPrices() : new HashMap<>();
            String usd = formatCurrency(car.getPriceUsd(), "$", 2);
            String eur = prices.containsKey("EUR") ? formatCurrency(prices.get("EUR"), "€", 2) : "N/A";
            String jpy = prices.containsKey("JPY") ? formatCurrency(prices.get("JPY"), "¥", 0) : "N/A";
            String gbp = prices.containsKey("GBP") ? formatCurrency(prices.get("GBP"), "£", 2) : "N/A";

            System.out.printf("%-20s %-15s %-10s %-12s %-12s %-12s %-12s %-12s%n",
                    brand, model, type, releaseDate, usd, eur, jpy, gbp);
        }
    }


    private static String nullSafe(String value) {
        return (value == null || value.isBlank()) ? "N/A" : value;
    }

    private static String formatCurrency(double amount, String symbol, int decimalPlaces) {
        return symbol + String.format("%." + decimalPlaces + "f", amount);
    }
}