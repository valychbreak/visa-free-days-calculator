package com.valychbreak.calculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.utils.ControllerTestDriver;
import com.valychbreak.calculator.utils.TestAuthTokenProvider;
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

import static com.valychbreak.calculator.utils.TestUtils.createUser;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@MicronautTest(transactional = false)
class GetTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    @Inject
    private TestAuthTokenProvider testAuthTokenProvider;

    @Inject
    private ControllerTestDriver controllerTestDriver;

    @Test
    void shouldReturnAllTravelPeriodsForUser() throws JSONException, JsonProcessingException {
        // given
        TravelPeriod travelPeriod = TravelPeriod.builder()
                .start(LocalDate.of(2020, 3, 11))
                .end(LocalDate.of(2020, 3, 15))
                .country("Poland")
                .note("Travel of testUser")
                .build();

        User testUser = controllerTestDriver.performTransactionalWrite(() -> {
            User createdUser = userRepository.save(createUser("testTravelPeriods"));
            travelPeriod.setUser(createdUser);
            travelPeriodRepository.save(travelPeriod);
            return createdUser;
        });

        String expectedResponse = objectMapper.writeValueAsString(List.of(travelPeriod));

        // when
        HttpRequest<Object> httpRequest = HttpRequest.GET("/period/all")
                .bearerAuth(testAuthTokenProvider.getToken(testUser));

        // then
        String response = client.toBlocking().retrieve(httpRequest);
        assertEquals(expectedResponse, response, true);
    }
}