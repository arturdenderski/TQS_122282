package tqs.arturdenderski.busticketsystem.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.arturdenderski.busticketsystem.data.Bus;
import tqs.arturdenderski.busticketsystem.data.City;
import tqs.arturdenderski.busticketsystem.data.Reservation;
import tqs.arturdenderski.busticketsystem.service.IBusTicketSystemService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class BusTicketSystemController {

    private final IBusTicketSystemService busTicketSystemService;

    @Autowired
    public BusTicketSystemController(IBusTicketSystemService busTicketSystemService) {
        this.busTicketSystemService = busTicketSystemService;
    }

    @GetMapping("/connections")
    public ResponseEntity<List<Bus>> getConnections(@RequestParam String originCity,
                                                    @RequestParam String destinationCity,
                                                    @RequestParam LocalDate departureDate) {
        log.info("Fetching connections from {} to {} on {}", originCity, destinationCity, departureDate);
        try {
            List<Bus> connections = busTicketSystemService.searchConnections(originCity, destinationCity, departureDate);
            return new ResponseEntity<>(connections, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching connections", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
        log.info("Fetching all cities");
        try {
            List<City> cities = busTicketSystemService.getAllCities();
            return new ResponseEntity<>(cities, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching cities", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> bookReservation(@RequestParam Long busId,
                                                  @RequestParam String passengerName,
                                                  @RequestParam String passengerSurname) {
        log.info("Booking reservation for passenger {} {}", passengerName, passengerSurname);

        if (busId == null || passengerName == null || passengerSurname == null) {
            return ResponseEntity.badRequest().body("Invalid request parameters");
        }

        try {
            Reservation reservation = busTicketSystemService.bookReservation(busId, passengerName, passengerSurname);
            if (reservation != null) {
                return new ResponseEntity<>("Reservation successful. Reservation token: " + reservation.getReservationToken(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to book reservation. Please try again.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Error booking reservation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error booking reservation");
        }
    }

    @GetMapping("/reservation/{reservationToken}")
    public ResponseEntity<Reservation> checkReservation(@PathVariable String reservationToken) {
        log.info("Checking reservation with token {}", reservationToken);
        try {
            Reservation reservation = busTicketSystemService.checkReservation(reservationToken);
            if (reservation != null) {
                return new ResponseEntity<>(reservation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error checking reservation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/connection/{connectionId}")
    public ResponseEntity<Bus> getConnectionDetails(@PathVariable Long connectionId) {
        log.info("Fetching connection details for ID {}", connectionId);
        try {
            Bus connection = busTicketSystemService.getConnectionById(connectionId);
            if (connection != null) {
                return new ResponseEntity<>(connection, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("Error fetching connection details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}