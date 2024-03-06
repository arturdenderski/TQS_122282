package tqs.carservice.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tqs.carservice.boundary.CarRestController;
import tqs.carservice.data.Car;
import tqs.carservice.service.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarRestController.class)
class CarRestController_WithMock_Test {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createCar_ValidInput_ShouldReturnCreatedStatus() throws Exception {
        Car car = new Car("Toyota", "Camry");
        when(carManagerService.save(Mockito.any())).thenReturn(car);

        mvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"maker\":\"Toyota\",\"model\":\"Camry\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllCars_ShouldReturnListOfCars() throws Exception {
        List<Car> cars = Arrays.asList(
                new Car("Toyota", "Camry"),
                new Car("Honda", "Civic")
        );
        when(carManagerService.getAllCars()).thenReturn(cars);

        mvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maker").value("Toyota"))
                .andExpect(jsonPath("$[1].maker").value("Honda"));
    }

    @Test
    void getCarById_ValidId_ShouldReturnCar() throws Exception {
        Car car = new Car("Toyota", "Camry");
        when(carManagerService.getCarDetails(1L)).thenReturn(java.util.Optional.of(car));

        mvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));
    }
}
