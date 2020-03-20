package com.valychbreak.calculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valychbreak.calculator.domain.TravelPeriod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@MicronautTest
class GetTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    RxHttpClient client;

    @Inject
    ObjectMapper objectMapper;

    @Test
    void shouldReturnAllTravelPeriods() throws JSONException, JsonProcessingException {
        String expected = objectMapper.writeValueAsString(List.of(
                TravelPeriod.builder()
                        .id(1L)
                        .start(LocalDate.of(2020, 3, 11))
                        .end(LocalDate.of(2020, 3, 15))
                        .country("Poland")
                        .note("Some note").build()
        ));

        MutableHttpRequest<Object> httpRequest = HttpRequest.GET("/period/all");
        String response = client.toBlocking().retrieve(httpRequest);

        assertEquals(expected, response, true);
    }
}