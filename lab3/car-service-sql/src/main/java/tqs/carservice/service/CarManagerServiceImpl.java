package tqs.carservice.service;

import org.springframework.stereotype.Service;
import tqs.carservice.data.Car;
import tqs.carservice.data.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerServiceImpl implements CarManagerService {

    private final CarRepository carRepository;

    public CarManagerServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> getCarDetails(Long carId) {
        return carRepository.findById(carId);
    }
}
