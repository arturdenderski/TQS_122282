package tqs.arturdenderski.busticketsystem.boundary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tqs.arturdenderski.busticketsystem.service.ICurrencyConversionService;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class CurrencyConversionController {

    private final ICurrencyConversionService currencyConversionService;

    @Autowired
    public CurrencyConversionController(ICurrencyConversionService currencyConversionService) {
        this.currencyConversionService = currencyConversionService;
    }

    @GetMapping("/currencies")
    public ResponseEntity<Map<String, Double>> getAllCurrencies() {
        Map<String, Double> currencies = currencyConversionService.getAllCurrencies();

        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }
}
