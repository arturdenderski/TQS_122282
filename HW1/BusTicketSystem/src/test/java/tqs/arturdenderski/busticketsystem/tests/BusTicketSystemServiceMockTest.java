package tqs.arturdenderski.busticketsystem.tests;

import org.junit.jupiter.api.Test;
import tqs.arturdenderski.busticketsystem.data.Bus;
import tqs.arturdenderski.busticketsystem.data.City;
import tqs.arturdenderski.busticketsystem.data.Reservation;
import tqs.arturdenderski.busticketsystem.service.IBusTicketSystemService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BusTicketSystemServiceMockTest {

    @Test
    public void testSearchConnections() {
        IBusTicketSystemService busTicketSystemService = mock(IBusTicketSystemService.class);

        List<Bus> expectedConnections = Arrays.asList(new Bus(), new Bus());
        when(busTicketSystemService.searchConnections("City A", "City B", LocalDate.of(2024, 4, 10)))
                .thenReturn(expectedConnections);

        List<Bus> actualConnections = busTicketSystemService.searchConnections("City A", "City B", LocalDate.of(2024, 4, 10));

        assertEquals(expectedConnections, actualConnections);
    }

    @Test
    public void testBookReservation() {
        IBusTicketSystemService busTicketSystemService = mock(IBusTicketSystemService.class);

        Reservation expectedReservation = new Reservation();
        when(busTicketSystemService.bookReservation(1L, "John", "Doe")).thenReturn(expectedReservation);

        Reservation actualReservation = busTicketSystemService.bookReservation(1L, "John", "Doe");

        assertEquals(expectedReservation, actualReservation);
    }

    @Test
    public void testCheckReservation() {
        IBusTicketSystemService busTicketSystemService = mock(IBusTicketSystemService.class);

        Reservation expectedReservation = new Reservation();
        when(busTicketSystemService.checkReservation("abc123")).thenReturn(expectedReservation);

        Reservation actualReservation = busTicketSystemService.checkReservation("abc123");

        assertEquals(expectedReservation, actualReservation);
    }

    @Test
    public void testGetAllCities() {
        IBusTicketSystemService busTicketSystemService = mock(IBusTicketSystemService.class);

        List<City> expectedCities = Arrays.asList(new City("City A"), new City("City B"));
        when(busTicketSystemService.getAllCities()).thenReturn(expectedCities);

        List<City> actualCities = busTicketSystemService.getAllCities();

        assertEquals(expectedCities, actualCities);
    }
}
