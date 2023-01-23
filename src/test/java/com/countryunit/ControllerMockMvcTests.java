package com.countryunit;


import com.countryunit.controller.CountryController;
import com.countryunit.entity.Country;
import com.countryunit.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.get;

@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockMvcTests.class})
public class ControllerMockMvcTests {
    @Autowired
    MockMvc mockMvc;
    @Mock
    CountryService countryService;
    @InjectMocks
    CountryController countryController;

    List<Country> myCountries;

    Country country;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.countryController).build();
    }

    //getAllCountries testing
    @Test
    @Order(1)
    public void test_getAllCountries() throws Exception {
        myCountries = new ArrayList<Country>();
        myCountries.add(new Country(1, "Nepal", "Kathmandu"));
        myCountries.add(new Country(1, "USA", "DC"));

        //mocking countryService
        when(countryService.getCountries()).thenReturn(myCountries);

        mockMvc.perform(MockMvcRequestBuilders.get("/countries"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andDo(print());
    }

    //getCountryById testing
    @Test
    @Order(2)
    public void test_getCountryById() throws Exception {
        country = new Country(2, "USA", "DC");
        int countryId = 2;
        when(countryService.getCountryById(countryId)).thenReturn(country);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/Countrys/{id}", countryId))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".counrtryId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".capital").value("DC"))
                .andDo(print());
    }

//    getCountryByName testing

    @Test
    @Order(3)
    public void test_getCountryByName() throws Exception {
        country = new Country(2, "USA", "DC");
        String countryName = "USA";
        when(countryService.getCountryByName(countryName)).thenReturn(country);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/getcountries/country").param("name", "USA"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".counrtryId").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("USA"))
                .andExpect(MockMvcResultMatchers.jsonPath(".capital").value("DC"))
                .andDo(print());
    }

    //addCountry testing
    @Test
    @Order(4)
    public void test_adddCountry() throws Exception {
        country = new Country(3, "Germany", "Berlin");

        //mocking the countryservice
        when(countryService.addCountry(country)).thenReturn(country);
        ObjectMapper mapper = new ObjectMapper();
        String jsonbody = mapper.writeValueAsString(country);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/createcountry")
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());


    }


    //    updateCountry testing
    @Test
    @Order(5)
    public void updateCountry() throws Exception {
        country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;
        //mocking the countryservice
        when(countryService.getCountryById(countryId)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);
        ObjectMapper mapper = new ObjectMapper();
        String jsonbody = mapper.writeValueAsString(country);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/updatecountry/{id}", countryId)
                        .content(jsonbody)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath(".countryName").value("Japan"))
                .andExpect(jsonPath(".capital").value("Tokyo"))
                .andDo(print());


    }


    //    deleteCountry testing
    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception {
        Country country = new Country(3, "Japan", "Tokyo");
        int countryId = 3;


//        mocking
        when(countryService.getCountryById(countryId)).thenReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/deletecountry/{id}", countryId))
                .andExpect(status().isOk());
    }

}

