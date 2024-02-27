import tqs.geocoding.AddressResolverService;
import tqs.geocoding.ISimpleHttpClient;
import tqs.geocoding.Address;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class AddressResolverServiceTest {

    private AddressResolverService addressResolverService;
    private ISimpleHttpClient httpClient;

    private final double latitude = 30.333472;
    private final double longitude = -81.470448;

    @BeforeEach
    void setUp() {
        httpClient = mock(ISimpleHttpClient.class);
        addressResolverService = new AddressResolverService(httpClient);
    }

    @Test
    void testFindAddressForLocation_Success() {
        String jsonResponse = "{\"results\":[{\"locations\":[{\"street\":\"12714 Ashley Melisse Blvd\",\"adminArea5\":\"Jacksonville\",\"road\":\"\",\"postalCode\":\"32225\"}]}]}";
        when(httpClient.doHttpGet(anyString())).thenReturn(jsonResponse);

        Optional<Address> result = addressResolverService.findAddressForLocation(latitude, longitude);

        assertTrue(result.isPresent());
        Address address = result.get();
        assertEquals("12714 Ashley Melisse Blvd", address.getRoad());
        assertEquals("Jacksonville", address.getCity());
        assertEquals("32225", address.getZipCode());
        assertEquals("", address.getHouseNumber());
    }

    @Test
    void testFindAddressForLocation_EmptyResponse() {
        when(httpClient.doHttpGet(anyString())).thenReturn("{}");

        Optional<Address> result = addressResolverService.findAddressForLocation(latitude, longitude);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAddressForLocation_NoResults() {
        when(httpClient.doHttpGet(anyString())).thenReturn("{\"results\":[]}");

        Optional<Address> result = addressResolverService.findAddressForLocation(latitude, longitude);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAddressForLocation_NoLocations() {
        when(httpClient.doHttpGet(anyString())).thenReturn("{\"results\":[{\"locations\":[]}]}");

        Optional<Address> result = addressResolverService.findAddressForLocation(latitude, longitude);

        assertFalse(result.isPresent());
    }

    @Test
    void testFindAddressForLocation_BadCoordinates() {
        double invalidLatitude = 200.0;
        double invalidLongitude = -1000.0;

        Optional<Address> result = addressResolverService.findAddressForLocation(invalidLatitude, invalidLongitude);

        assertFalse(result.isPresent());
    }
}
