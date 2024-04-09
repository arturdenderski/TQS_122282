package tqs.arturdenderski.busticketsystem.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tqs.arturdenderski.busticketsystem.data.Bus;
import tqs.arturdenderski.busticketsystem.data.BusRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusRepositoryTest {

    @Mock
    private BusRepository busRepository;

    @Test
    public void testFindById() {
        Long busId = 1L;
        Bus bus = new Bus();
        bus.setId(busId);
        when(busRepository.findById(busId)).thenReturn(Optional.of(bus));

        Optional<Bus> result = busRepository.findById(busId);

        assertEquals(busId, result.get().getId());
    }

    @Test
    public void testFindByOriginCityNameAndDestinationCityNameAndDepartureDate() {
        String originCityName = "CityA";
        String destinationCityName = "CityB";
        LocalDate departureDate = LocalDate.now();
        List<Bus> expectedBuses = Arrays.asList(new Bus(), new Bus());
        when(busRepository.findByOriginCityNameAndDestinationCityNameAndDepartureDate(originCityName, destinationCityName, departureDate))
                .thenReturn(expectedBuses);

        List<Bus> result = busRepository.findByOriginCityNameAndDestinationCityNameAndDepartureDate(originCityName, destinationCityName, departureDate);

        assertEquals(expectedBuses.size(), result.size());
    }
    @Test
    public void testFindById_Error() {
        Long busId = 1L;
        when(busRepository.findById(busId)).thenThrow(new RuntimeException("Error occurred while fetching bus by ID"));

        try {
            Optional<Bus> result = busRepository.findById(busId);
            fail("Expected an exception to be thrown");
        } catch (RuntimeException e) {
            assertEquals("Error occurred while fetching bus by ID", e.getMessage());
        }
    }
}
