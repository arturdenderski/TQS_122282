package tqs.carservice.service;

import tqs.carservice.data.Car;

import java.util.List;
import java.util.Optional;

public interface CarManagerService {

    Car save(Car car);

    List<Car> getAllCars();

    Optional<Car> getCarDetails(Long carId);
}
