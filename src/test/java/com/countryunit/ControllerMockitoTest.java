package com.countryunit;


import com.countryunit.controller.CountryController;
import com.countryunit.entity.Country;
import com.countryunit.service.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTest.class})
public class ControllerMockitoTest {
    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> mycountries;
    Country country;

    @Test
    @Order(1)
    public void test_getAllCountries() {
        ArrayList<Country> mycountries = new ArrayList<>();
        mycountries.add(new Country(1, "Nepal", "Kathmandu"));
        mycountries.add(new Country(1, "USA", "DC"));

        //mocking service
        when(countryService.getCountries()).thenReturn(mycountries);
        ResponseEntity<List<Country>> res = countryController.getAllCountries();
        assertEquals(HttpStatus.FOUND, res.getStatusCode());
        assertEquals(2, res.getBody().size());

    }

    @Test
    @Order(2)
    public void test_getCountyyById() {
        country = new Country(1, "Nepal", "Kathmandu");
        int countryId = 1;

//Mocking countryService
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Optional<Country>> res = countryController.getCountryById(countryId);

        assertEquals(HttpStatus.FOUND, res.getStatusCode());
        assertEquals(countryId, res.getBody().get().getCounrtryId());

    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        country = new Country(1, "Nepal", "kathmandu");
        String countryName = "Nepal";

        //Mocking countryservice
        when(countryService.getCountryByName(countryName)).thenReturn(country);
        ResponseEntity<Country> res = countryController.getCountryByName(countryName);

        assertEquals(HttpStatus.FOUND, res.getStatusCode());
        assertEquals(countryName, res.getBody().getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        country = new Country(3, "Germany", "Berlin");

        //mocking countryService
        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> res = countryController.addCountry(country);

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertEquals(country, res.getBody());

    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;

//mocking countryService
        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ResponseEntity<Country> res = countryController.updateCountry(countryId, country);
        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals(3, res.getBody().getCounrtryId());
        assertEquals("Japan", res.getBody().getCountryName());
        assertEquals("Tokyo", res.getBody().getCapital());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        country = new Country(3, "japan", "Tokyo");
        int countryId = 3;
//        mocking countryservice
        when(countryService.getCountryById(countryId)).thenReturn(country);
        ResponseEntity<Country> res = countryController.deleteCountry(countryId);
        assertEquals(HttpStatus.OK, res.getStatusCode());
    }
}

