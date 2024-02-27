package tqs.geocoding;

import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;

public class AddressResolverService {
    private final ISimpleHttpClient httpClient;

    public AddressResolverService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private String prepareUriForRemoteEndpoint(double latitude, double longitude) {
        return String.format("https://www.mapquestapi.com/geocoding/v1/reverse?key=YOUR_API_KEY&location=%.6f,%.6f", latitude, longitude);
    }

    public Optional<Address> findAddressForLocation(double latitude, double longitude) {
        String url = prepareUriForRemoteEndpoint(latitude, longitude);
        String response = httpClient.doHttpGet(url);
        if (response == null) {
            return Optional.empty();
        }
        return parseApiResponse(response);
    }

    private Optional<Address> parseApiResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            if(jsonResponse.isEmpty()) {
                return Optional.empty();
            }
            JSONArray results = jsonResponse.getJSONArray("results");
            if (results.isEmpty()) {
                return Optional.empty();
            }
            JSONObject firstResult = results.getJSONObject(0);
            JSONArray locations = firstResult.getJSONArray("locations");
            if (locations.isEmpty()) {
                return Optional.empty();
            }
            JSONObject firstLocation = locations.getJSONObject(0);
            String street = firstLocation.optString("street");
            String city = firstLocation.optString("adminArea5");
            String zipCode = firstLocation.optString("postalCode");
            String houseNumber = ""; // Not provided in the response
            return Optional.of(new Address(street, city, zipCode, houseNumber));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
