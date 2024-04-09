package tqs.arturdenderski.busticketsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tqs.arturdenderski.busticketsystem.data.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
public class BusTicketSystemService implements IBusTicketSystemService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<Bus> searchConnections(String originCityName, String destinationCityName, LocalDate date) {
        try {
            return busRepository.findByOriginCityNameAndDestinationCityNameAndDepartureDate(originCityName, destinationCityName, date);
        } catch (Exception e) {
            log.error("Error searching connections", e);
            return null;
        }
    }

    @Override
    public Reservation bookReservation(Long busId, String passengerName, String passengerSurname) {
        try {
            Bus bus = busRepository.findById(busId).orElse(null);
            if (bus == null || bus.getAvailableSeats() == 0 &&
                    (bus.getDepartureDate().isEqual(LocalDate.now()) &&
                            bus.getDepartureTime().isBefore(LocalTime.now()))) {
                return null;
            }

            bus.setAvailableSeats(bus.getAvailableSeats() - 1);
            busRepository.save(bus);

            Reservation reservation = new Reservation(bus, passengerName, passengerSurname);
            return reservationRepository.save(reservation);
        } catch (Exception e) {
            log.error("Error booking reservation", e);
            return null;
        }
    }

    @Override
    public Reservation checkReservation(String reservationToken) {
        try {
            return reservationRepository.findByReservationToken(reservationToken).orElse(null);
        } catch (Exception e) {
            log.error("Error checking reservation", e);
            return null;
        }
    }

    @Override
    public Bus getConnectionById(Long connectionId) {
        try {
            return busRepository.findById(connectionId).orElse(null);
        } catch (Exception e) {
            log.error("Error getting connection by ID", e);
            return null;
        }
    }

    @Override
    public List<City> getAllCities() {
        try {
            return cityRepository.findAll();
        } catch (Exception e) {
            log.error("Error getting all cities", e);
            return null;
        }
    }
}
