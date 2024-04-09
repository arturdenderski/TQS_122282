package tqs.arturdenderski.busticketsystem.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<Bus, Long> {

    Optional<Bus> findById(Long busId);

    List<Bus> findByOriginCityNameAndDestinationCityNameAndDepartureDate(String originCityName, String destinationCityName, LocalDate departureDate);
}
