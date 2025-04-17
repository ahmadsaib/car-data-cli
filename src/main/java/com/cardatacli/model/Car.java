package com.cardatacli.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.Map;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {
    private String type;
    private String model;
    private String brand;
    private LocalDate releaseDate;
    private double priceUsd;
    private Map<String, Double> prices;

    public Car(String type, String model, String brand, LocalDate releaseDate, double priceUsd, Map<String, Double> prices) {
        this.type = type;
        this.model = model;
        this.brand = brand;
        this.releaseDate = releaseDate;
        this.priceUsd = priceUsd;
        this.prices = prices;
    }

    public Car() {

    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }

    public double getPriceUsd() { return priceUsd; }
    public void setPriceUsd(double priceUsd) { this.priceUsd = priceUsd; }

    public Map<String, Double> getPrices() { return prices; }
    public void setPrices(Map<String, Double> prices) { this.prices = prices; }

    @Override
    public String toString() {
        return String.format("%s %s (%s) - USD %.2f",
                brand != null ? brand : "Unknown",
                model,
                releaseDate != null ? releaseDate.toString() : "N/A",
                priceUsd);
    }
}
