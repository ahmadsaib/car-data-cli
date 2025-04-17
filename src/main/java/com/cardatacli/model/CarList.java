package com.cardatacli.model;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarList {
    @XmlElement(name = "car")
    private List<Car> cars;

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public CarList() {}

    public CarList(List<Car> cars) {
        this.cars = cars;
    }
}

