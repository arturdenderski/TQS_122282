package tqs.arturdenderski.busticketsystem.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tqs.arturdenderski.busticketsystem.data.*;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BusTicketSystemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testGetConnections_Success() throws Exception {
        City originCity = new City("OriginCity");
        City destinationCity = new City("DestinationCity");
        cityRepository.save(originCity);
        cityRepository.save(destinationCity);

        Bus busTrip = new Bus();
        busTrip.setOriginCity(originCity);
        busTrip.setDestinationCity(destinationCity);
        busTrip.setDepartureDate(LocalDate.now());
        busTrip.setPriceInEuro(10.0);
        busRepository.save(busTrip);

        mockMvc.perform(get("/api/connection/{connectionId}", busTrip.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originCity.name").value("OriginCity"))
                .andExpect(jsonPath("$.destinationCity.name").value("DestinationCity"))
                .andExpect(jsonPath("$.departureDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.priceInEuro").value(10.0));
    }

    @Test
    public void testGetConnections_InvalidRequestParams() throws Exception {
        mockMvc.perform(get("/api/connections")
                        .param("originCity", "")
                        .param("destinationCity", "DestinationCity")
                        .param("departureDate", LocalDate.now().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetAllCities_Success() throws Exception {
        City city1 = new City("City1");
        City city2 = new City("City2");
        cityRepository.save(city1);
        cityRepository.save(city2);

        mockMvc.perform(get("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].name").value("City1"))
                .andExpect(jsonPath("$.[1].name").value("City2"));
    }

    @Test
    public void testBookReservation_Success() throws Exception {
        City originCity = new City("OriginCity");
        City destinationCity = new City("DestinationCity");
        cityRepository.save(originCity);
        cityRepository.save(destinationCity);

        Bus bus = new Bus();
        bus.setOriginCity(originCity);
        bus.setDestinationCity(destinationCity);
        bus.setDepartureDate(LocalDate.now());
        bus.setTotalSeats(50);
        bus.setAvailableSeats(50);
        bus.setPriceInEuro(10.0);
        busRepository.save(bus);

        mockMvc.perform(post("/api/reserve")
                        .param("busId", bus.getId().toString())
                        .param("passengerName", "John")
                        .param("passengerSurname", "Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testBookReservation_InvalidRequestParams() throws Exception {
        mockMvc.perform(post("/api/reserve")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCheckReservation_Success() throws Exception {
        City originCity = new City("OriginCity");
        City destinationCity = new City("DestinationCity");
        cityRepository.save(originCity);
        cityRepository.save(destinationCity);

        Bus bus = new Bus();
        bus.setOriginCity(originCity);
        bus.setDestinationCity(destinationCity);
        bus.setDepartureDate(LocalDate.now());
        bus.setTotalSeats(50);
        bus.setAvailableSeats(50);
        bus.setPriceInEuro(10.0);
        busRepository.save(bus);

        Reservation reservation = new Reservation();
        reservation.setReservationToken("token123");
        reservation.setBus(bus);
        reservationRepository.save(reservation);

        mockMvc.perform(get("/api/reservation/{reservationToken}", "token123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationToken").value("token123"));
    }


    @Test
    public void testCheckReservation_NotFound() throws Exception {
        mockMvc.perform(get("/api/reservation/{reservationToken}", "nonexistent_token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetConnectionDetails_Success() throws Exception {
        City originCity = new City("OriginCity");
        City destinationCity = new City("DestinationCity");
        cityRepository.save(originCity);
        cityRepository.save(destinationCity);

        Bus busTrip = new Bus();
        busTrip.setOriginCity(originCity);
        busTrip.setDestinationCity(destinationCity);
        busTrip.setDepartureDate(LocalDate.now());
        busTrip.setPriceInEuro(10.0);
        busRepository.save(busTrip);

        mockMvc.perform(get("/api/connection/{connectionId}", busTrip.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originCity.name").value("OriginCity"))
                .andExpect(jsonPath("$.destinationCity.name").value("DestinationCity"))
                .andExpect(jsonPath("$.departureDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.priceInEuro").value(10.0));
    }

    @Test
    public void testBookReservation_InvalidBusId() throws Exception {
        mockMvc.perform(post("/api/reserve")
                        .param("busId", "-1") // Invalid bus ID
                        .param("passengerName", "John")
                        .param("passengerSurname", "Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testBookReservation_InsufficientAvailableSeats() throws Exception {
        City originCity = new City("OriginCity");
        City destinationCity = new City("DestinationCity");
        cityRepository.save(originCity);
        cityRepository.save(destinationCity);

        Bus bus = new Bus();
        bus.setOriginCity(originCity);
        bus.setDestinationCity(destinationCity);
        bus.setDepartureDate(LocalDate.now());
        bus.setTotalSeats(1); // Total seats set to 1
        bus.setAvailableSeats(0); // No available seats
        bus.setPriceInEuro(10.0);
        busRepository.save(bus);

        mockMvc.perform(post("/api/reserve")
                        .param("busId", bus.getId().toString())
                        .param("passengerName", "John")
                        .param("passengerSurname", "Doe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
