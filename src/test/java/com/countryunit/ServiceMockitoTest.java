package com.countryunit;

import com.countryunit.entity.Country;
import com.countryunit.repository.CountryRepository;
import com.countryunit.service.CountryService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceMockitoTest {
    @Mock
    CountryRepository countryRepository;
    @InjectMocks
    CountryService countryService;

    public List<Country> countries;

    @Test
    @Order(1)
    public void test_getCountries() {
        ArrayList<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "Nepal", "Kathmandu"));
        myCountries.add(new Country(2, "USA", "DC"));
        for (Country desh : myCountries) {
            System.out.println(desh);
        }
        // Mocking countryRepository
        when(countryRepository.findAll()).thenReturn(myCountries);

        assertEquals(2, countryService.getCountries().size());

    }

    @Test
    @Order(2)
    public void test_getCountryById() {
        Optional<Country> myCountries = new ArrayList<>();
        myCountries.add(new Country(1, "Nepal", "Kathmandu"));
        myCountries.add(new Country(2, "USA", "DC"));
        int countryId = 1;
        // Mocking countryRepository
        when(countryRepository.findById(countryId)).thenReturn(myCountries);

        assertEquals(countryId, countryService.getCountryById(countryId).get());

    }

}
