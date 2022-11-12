package com.countryunit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.countryunit.entity.Country;
import com.countryunit.repository.CountryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    // get all countries

    public List<Country> getCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries;
    }


    // get country by country id
    public Country getCountryById(Integer countryId) {
        List<Country> countries = countryRepository.findAll();
        Country country = null;

        for (Country con : countries
        ) {
            if (con.getCounrtryId() == countryId) {
                country = con;
            }
        }
        return country;
    }

    //get countrybyid
    public Country getCountryById2(Integer countryId) {
        Country byId = countryRepository.findById(countryId).orElse(null);
        return byId;
    }

    public List<Country> getCountryByName2(String countryName) {
        List<Country> byName = (List<Country>) countryRepository.findByName(countryName);
        return byName;
    }


    //get country by country name
    public Country getCountryByName(String countryName) {
        List<Country> countries = countryRepository.findAll();
        Country country = null;
//        for (int i = 0; i <countries.toArray().length ; i++) {
//            if (con.getCountryName().equalsIgnoreCase(countryName)) {
//                country=con;
//            }
//        }
        for (Country con : countries
        ) {
            if (con.getCountryName().equalsIgnoreCase(countryName)) {
                country = con;
            }
        }
        return country;
    }

    // save the country
    public Country addCountry(Country country) {
        Country save = countryRepository.save(country);
        return save;
    }

    // delete the country by id

    public void delete(Country country) {
        countryRepository.delete(country);

    }

    // update country
    public Country updateCountry(Country country) {
        return countryRepository.save(country);

    }

//    public Country updateCountry(Country country) {
//        Country updatedCountry = countryRepository.findById(country.getCounrtryId()).orElse(null);
//
//        updatedCountry.setCountryName(country.getCountryName());
//        updatedCountry.setCapital(country.getCapital());
//
//        return updatedCountry;
//    }

}
