package com.valychbreak.calculator.controller;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@MicronautTest
class GetTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    RxHttpClient client;

    @Test
    void shouldReturnAllTravelPeriods() throws JSONException {
        MutableHttpRequest<Object> httpRequest = HttpRequest.GET("/period/all");

        String response = client.toBlocking().retrieve(httpRequest);

        assertEquals("[{\"id\": 1,\"start\":[2020,3,11],\"end\":[2020,3,15],\"country\":\"Poland\",\"note\":\"Some note\"}]", response, true);
    }
}