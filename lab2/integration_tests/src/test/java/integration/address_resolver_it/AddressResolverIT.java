package integration.address_resolver_it;

import org.junit.jupiter.api.Test;
import tqs.geocoding.AddressResolverService;
import tqs.geocoding.ISimpleHttpClient;
import tqs.geocoding.Address;
import tqs.geocoding.TqsBasicHttpClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AddressResolverIT {

    private final ISimpleHttpClient httpClient = new TqsBasicHttpClient();

    private final AddressResolverService addressResolverService = new AddressResolverService(httpClient);

    @Test
    void testFindAddressForLocation_Success() {
        double latitude = 30.333472;
        double longitude = -81.470448;

        Optional<Address> result = addressResolverService.findAddressForLocation(latitude, longitude);

        assertTrue(result.isPresent());
        Address address = result.get();
        assertEquals("802 Arkenstone Dr", address.getRoad());
        assertEquals("Jacksonville", address.getCity());
        assertEquals("32225", address.getZipCode());
        assertEquals("", address.getHouseNumber());
    }

    @Test
    void testFindAddressForLocation_BadCoordinates() {
        double invalidLatitude = 200.0;
        double invalidLongitude = -1000.0;

        Optional<Address> result = addressResolverService.findAddressForLocation(invalidLatitude, invalidLongitude);

        assertFalse(result.isPresent());
    }
}
