package tqs.carservice.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.carservice.data.Car;
import tqs.carservice.data.CarRepository;
import tqs.carservice.service.CarManagerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CarService_WithMock_Test {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerServiceImpl carManagerService;

    @BeforeEach
    public void setUp() {
        Car toyotaCamry = new Car("Toyota", "Camry");
        toyotaCamry.setCarId(1L);

        Car hondaCivic = new Car("Honda", "Civic");
        hondaCivic.setCarId(2L);

        List<Car> allCars = Arrays.asList(toyotaCamry, hondaCivic);

        Mockito.when(carRepository.save(toyotaCamry)).thenReturn(toyotaCamry);
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findById(1L)).thenReturn(Optional.of(toyotaCamry));
        Mockito.when(carRepository.findById(2L)).thenReturn(Optional.of(hondaCivic));
        Mockito.when(carRepository.findById(3L)).thenReturn(Optional.empty());
    }

    @Test
    void saveCar_ShouldReturnSavedCar() {
        Car toyotaCamry = new Car("Toyota", "Camry");
        toyotaCamry.setCarId(1L);

        Car result = carManagerService.save(toyotaCamry);

        assertThat(result).isEqualTo(toyotaCamry);
    }

    @Test
    void getAllCars_ShouldReturnListOfCars() {
        Car toyotaCamry = new Car("Toyota", "Camry");
        toyotaCamry.setCarId(1L);

        Car hondaCivic = new Car("Honda", "Civic");
        hondaCivic.setCarId(2L);

        List<Car> expectedResult = Arrays.asList(toyotaCamry, hondaCivic);

        List<Car> result = carManagerService.getAllCars();

        assertThat(result).containsExactlyInAnyOrderElementsOf(expectedResult);
    }

    @Test
    void getCarDetails_WithValidCarId_ShouldReturnCar() {
        Car toyotaCamry = new Car("Toyota", "Camry");
        toyotaCamry.setCarId(1L);

        Car result = carManagerService.getCarDetails(1L).orElse(null);

        assertThat(result).isEqualTo(toyotaCamry);
    }

    @Test
    void getCarDetails_WithInvalidCarId_ShouldReturnEmptyOptional() {
        Car result = carManagerService.getCarDetails(3L).orElse(null);

        assertThat(result).isNull();
    }
}
