package tqs.carservice.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tqs.carservice.data.Car;
import tqs.carservice.data.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CarRestController_TemplateIT_Test {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car toyotaCamry = new Car("Toyota", "Camry");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", toyotaCamry, Car.class);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Toyota");
    }

    @Test
    void givenCars_whenGetAllCars_thenStatus200() {
        Car toyotaCamry = new Car("Toyota", "Camry");
        Car hondaCivic = new Car("Honda", "Civic");

        carRepository.save(toyotaCamry);
        carRepository.save(hondaCivic);

        ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Car>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void givenExistingCarId_whenGetCarById_thenStatus200() {
        Car toyotaCamry = new Car("Toyota", "Camry");
        carRepository.save(toyotaCamry);

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/{carId}", Car.class, toyotaCamry.getCarId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(toyotaCamry);
    }

    @Test
    void givenNonExistingCarId_whenGetCarById_thenStatus404() {
        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/{carId}", Car.class, 999);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}
