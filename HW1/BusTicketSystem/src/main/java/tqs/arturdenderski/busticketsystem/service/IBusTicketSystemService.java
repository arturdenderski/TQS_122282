package tqs.arturdenderski.busticketsystem.service;

import tqs.arturdenderski.busticketsystem.data.Bus;
import tqs.arturdenderski.busticketsystem.data.City;
import tqs.arturdenderski.busticketsystem.data.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface IBusTicketSystemService {
    List<Bus> searchConnections(String originCityName, String destinationCityName, LocalDate date);

    Reservation bookReservation(Long busId, String passengerName, String passengerSurname);

    Reservation checkReservation(String reservationToken);

    List<City> getAllCities();

    Bus getConnectionById(Long connectionId);
}