package tqs.carservice.tests;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tqs.carservice.boundary.CarRestController;
import tqs.carservice.data.Car;
import tqs.carservice.service.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;

@WebMvcTest(CarRestController.class)
@AutoConfigureTestDatabase
class CarRestController_REST_Assured_Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @AfterEach
    public void resetDb() {
        // Reset any data if necessary
    }

    @Test
    void givenCars_whenGetAllCars_thenStatus200() {
        // Mocking data
        Car toyotaCamry = new Car("Toyota", "Camry");
        Car hondaCivic = new Car("Honda", "Civic");
        List<Car> cars = Arrays.asList(toyotaCamry, hondaCivic);
        when(carManagerService.getAllCars()).thenReturn(cars);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/cars")
                .then()
                .statusCode(200);
    }
}
