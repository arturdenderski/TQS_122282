package tqs.arturdenderski.busticketsystem.tests;

import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import tqs.arturdenderski.busticketsystem.data.Bus;
import tqs.arturdenderski.busticketsystem.data.BusRepository;
import tqs.arturdenderski.busticketsystem.data.City;
import tqs.arturdenderski.busticketsystem.data.Reservation;
import tqs.arturdenderski.busticketsystem.data.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void testFindByOriginCityNameAndDestinationCityName() {
        City originCity = new City("City A");
        City destinationCity = new City("City B");
        entityManager.persist(originCity);
        entityManager.persist(destinationCity);
        entityManager.flush();

        Bus bus1 = new Bus();
        bus1.setOriginCity(originCity);
        bus1.setDestinationCity(destinationCity);
        bus1.setDepartureDate(LocalDate.of(2024, 4, 10));
        bus1.setTotalSeats(50);
        bus1.setAvailableSeats(50);
        bus1.setPriceInEuro(25.0);
        entityManager.persist(bus1);

        Bus bus2 = new Bus();
        bus2.setOriginCity(originCity);
        bus2.setDestinationCity(destinationCity);
        bus2.setDepartureDate(LocalDate.of(2024, 4, 10));
        bus2.setTotalSeats(50);
        bus2.setAvailableSeats(50);
        bus2.setPriceInEuro(30.0);
        entityManager.persist(bus2);

        entityManager.flush();

        List<Bus> foundBuses = busRepository.findByOriginCityNameAndDestinationCityNameAndDepartureDate("City A", "City B", LocalDate.of(2024, 4, 10));

        assertEquals(2, foundBuses.size());
    }

    @Test
    public void testSaveAndRetrieveReservation() {
        City originCity = new City("City A");
        City destinationCity = new City("City B");
        entityManager.persist(originCity);
        entityManager.persist(destinationCity);
        entityManager.flush();

        Bus bus = new Bus();
        bus.setOriginCity(originCity);
        bus.setDestinationCity(destinationCity);
        bus.setDepartureDate(LocalDate.of(2024, 4, 10));
        bus.setTotalSeats(50);
        bus.setAvailableSeats(50);
        bus.setPriceInEuro(25.0);
        bus = entityManager.persist(bus);

        Reservation reservation = new Reservation();
        reservation.setBus(bus);
        reservation.setPassengerName("John");
        reservation.setPassengerSurname("Doe");

        Reservation savedReservation = entityManager.persist(reservation);

        Reservation retrievedReservation = reservationRepository.findByReservationToken(savedReservation.getReservationToken()).orElse(null);

        assertNotNull(retrievedReservation);
        assertEquals(savedReservation.getId(), retrievedReservation.getId());
        assertEquals(bus.getId(), retrievedReservation.getBus().getId());
        assertEquals("John", retrievedReservation.getPassengerName());
        assertEquals("Doe", retrievedReservation.getPassengerSurname());
    }

    @Test
    public void testFindByReservationToken_WhenReservationNotExists() {
        Reservation retrievedReservation = reservationRepository.findByReservationToken("nonexistent-token").orElse(null);

        assertNull(retrievedReservation);
    }

    @Test
    public void testFindByOriginCityNameAndDestinationCityName_WithInvalidQueryParameter() {
        List<Bus> foundBuses = busRepository.findByOriginCityNameAndDestinationCityNameAndDepartureDate("Invalid City", "City B", LocalDate.of(2024, 4, 10));

        assertTrue(foundBuses.isEmpty());
    }
}
