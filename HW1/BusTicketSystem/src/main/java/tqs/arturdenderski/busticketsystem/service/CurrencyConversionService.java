package tqs.arturdenderski.busticketsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CurrencyConversionService implements ICurrencyConversionService {

    @Value("${currencyApiUrl}")
    private String currencyApiUrl;

    @Override
    @Cacheable(cacheNames = "currencies")
    public Map<String, Double> getAllCurrencies() {
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                currencyApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {});

        Map<String, Object> responseBody = responseEntity.getBody();
        Map<String, Double> currencies = new HashMap<>();

        if (responseBody != null && responseBody.containsKey("rates")) {
            Map<String, Object> rates = (Map<String, Object>) responseBody.get("rates");
            for (Map.Entry<String, Object> entry : rates.entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Integer) {
                    currencies.put(entry.getKey(), ((Integer) value).doubleValue());
                } else if (value instanceof Double) {
                    currencies.put(entry.getKey(), (Double) value);
                } else {
                }
            }
        }
        log.info("Fetching currencies");

        return currencies;
    }

    @Scheduled(fixedRate = 10000)
    @CacheEvict(cacheNames = "currencies", allEntries = true)
    public void emptyCurrenciesCache() {
        log.info("Emptying currencies cache");
    }
}
