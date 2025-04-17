package com.cardatacli.service;

import com.cardatacli.model.Car;

import java.util.List;

public interface CarServiceInterface {

    List<Car> filterCars(List<Car> cars, String filter);

    List<Car> sortCars(List<Car> cars, String sortOption);
}

