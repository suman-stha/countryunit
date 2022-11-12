package com.countryunit.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.countryunit.entity.Country;
import com.countryunit.service.CountryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    // get all countries
    // get all employees
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        try {
            List<Country> countries = countryService.getCountries();
            return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/country")//country is query parameter
    public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String countryName) {
        try {
            Country countryByName = countryService.getCountryByName(countryName);
            return new ResponseEntity<Country>(countryByName, HttpStatus.FOUND);

        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // create country rest api
    @PostMapping("/createcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        try {
            Country countryAdded = countryService.addCountry(country);
            return new ResponseEntity<Country>(countryAdded, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // get Country by id rest api
    @GetMapping("/Countrys/{id}")//id is path parameter
    public ResponseEntity<Optional<Country>> getCountryById(@PathVariable Integer id) {
        Optional<Country> CountryById = Optional.ofNullable(countryService.getCountryById(id));
        return new ResponseEntity<>(CountryById, HttpStatus.FOUND);
    }

    // update Country rest api
    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(name = "id") Integer id, @RequestBody Country country) {
        Country existingCountry = countryService.getCountryById(id);
        existingCountry.setCountryName(country.getCountryName());
        existingCountry.setCapital(country.getCapital());

        Country updatedCountry = countryService.updateCountry(existingCountry);
        return new ResponseEntity<Country>(updatedCountry, HttpStatus.OK);
    }

    // delete Country rest api
//    @DeleteMapping("/Countrys")
//    public void deleteCountry(@PathVariable Country country) {
//        countryService.delete(country);
//    }

    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable("id") Integer id) {
        Country country = null;
        try {
            country = countryService.getCountryById(id);
            countryService.delete(country);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Country>(country, HttpStatus.OK);

    }
}
