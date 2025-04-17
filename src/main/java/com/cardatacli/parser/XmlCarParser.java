package com.cardatacli.parser;

import com.cardatacli.model.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;

public class XmlCarParser {

    private static final Logger log = LoggerFactory.getLogger(XmlCarParser.class);

    public static List<Car> parse(File xmlFile) {
        List<Car> cars = new ArrayList<>();

        try {
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(xmlFile);

            doc.getDocumentElement().normalize();
            NodeList carNodes = doc.getElementsByTagName("car");

            for (int i = 0; i < carNodes.getLength(); i++) {
                Node node = carNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    cars.add(parseCarElement(element));
                }
            }

        } catch (Exception e) {
            log.error("Failed to parse XML file '{}': {}", xmlFile.getName(), e.getMessage(), e);
        }

        return cars;
    }

    private static Car parseCarElement(Element element) {
        Car car = new Car();

        try {
            car.setType(getTagText(element, "type"));
            car.setModel(getTagText(element, "model"));

            String basePrice = getTagText(element, "price");
            if (basePrice != null && !basePrice.isBlank()) {
                car.setPriceUsd(Double.parseDouble(basePrice));
            }

            Map<String, Double> prices = extractCurrencyPrices(element);
            car.setPrices(prices);

        } catch (Exception e) {
            log.warn("Skipping invalid car entry: {}", e.getMessage());
        }

        return car;
    }

    private static Map<String, Double> extractCurrencyPrices(Element element) {
        Map<String, Double> prices = new HashMap<>();

        NodeList priceNodes = element.getElementsByTagName("price");

        for (int j = 0; j < priceNodes.getLength(); j++) {
            Node priceNode = priceNodes.item(j);
            if (priceNode.getNodeType() == Node.ELEMENT_NODE) {
                Element priceEl = (Element) priceNode;
                if (priceEl.hasAttribute("currency")) {
                    String currency = priceEl.getAttribute("currency");
                    String valueStr = priceEl.getTextContent().trim();
                    try {
                        double value = Double.parseDouble(valueStr);
                        prices.put(currency, value);
                    } catch (NumberFormatException ex) {
                        log.warn("Invalid price format '{}' for currency '{}'", valueStr, currency);
                    }
                }
            }
        }

        return prices;
    }

    private static String getTagText(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() > 0 && nodes.item(0).getTextContent() != null) {
            return nodes.item(0).getTextContent().trim();
        }
        return null;
    }
}