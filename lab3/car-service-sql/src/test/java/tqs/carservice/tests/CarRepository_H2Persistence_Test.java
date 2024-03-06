package tqs.carservice.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.carservice.data.Car;
import tqs.carservice.data.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepository_H2Persistence_Test {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindCarByCarId_thenReturnCar() {
        Car car = new Car("Toyota", "Camry");
        entityManager.persistAndFlush(car);
        Car foundCar = carRepository.findByCarId(car.getCarId());
        assertThat(foundCar).isNotNull();
        assertThat(foundCar.getCarId()).isEqualTo(car.getCarId());
    }

    @Test
    void whenInvalidCarId_thenReturnNull() {
        Car foundCar = carRepository.findByCarId(-1L);
        assertThat(foundCar).isNull();
    }

    @Test
    void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Ford", "Focus");
        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.flush();
        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(2)
                .extracting(Car::getCarId)
                .contains(car1.getCarId(), car2.getCarId());
    }
}
