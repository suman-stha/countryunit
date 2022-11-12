package com.countryunit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.countryunit.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
Country findByName(String countryName);
}
