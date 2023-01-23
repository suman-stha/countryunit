package com.countryunit;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerIntegrationTest {

    @Test
    @Order(1)
    void getAllCountriesIntegrationTest() throws JSONException {

        String expected = "[{\n" +
                "    \"id\":1,\n" +
                "    \"countryName\":\"Nepal\",\n" +
                "    \"capital\":\"Kathmandu\"\n" +
                "},{\n" +
                "    \n" +
                "    \"id\":1,\n" +
                "    \"countryName\":\"USA\",\n" +
                "    \"capital\":\"DC\"\n" +
                "}\n" +
                "]";
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/countries", String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        JSONAssert.assertEquals(expected, response.getBody(), false);


    }

}
