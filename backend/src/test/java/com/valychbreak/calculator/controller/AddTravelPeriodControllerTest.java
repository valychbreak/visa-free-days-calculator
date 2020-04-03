package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriodDTO;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.utils.TestAuthTokenProvider;
import io.micronaut.http.*;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.valychbreak.calculator.utils.TestUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class AddTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TestAuthTokenProvider authTokenProvider;

    @Test
    void shouldCreateNewPeriod() {
        TravelPeriodDTO travelPeriodDTO = createTravelPeriodDTO();

        User user = createUser("createNewPeriodUser");
        userRepository.save(user);

        MutableHttpRequest<TravelPeriodDTO> httpRequest = HttpRequest.POST("/period/add", travelPeriodDTO)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .bearerAuth(authTokenProvider.getToken(user));

        HttpResponse<TravelPeriodDTO> response = client.toBlocking()
                .exchange(httpRequest, TravelPeriodDTO.class);

        TravelPeriodDTO actualTravelPeriod = response.getBody().orElseThrow();
        assertThat(actualTravelPeriod.getId()).isNotNull();
        assertThat(actualTravelPeriod).isEqualToIgnoringGivenFields(travelPeriodDTO, "id");

        assertThat(travelPeriodRepository.findById(actualTravelPeriod.getId())).isPresent();
        assertThat(userRepository.findByUsername(user.getUsername()).orElseThrow()
                .getTravelPeriods()
        ).extracting("id")
                .contains(actualTravelPeriod.getId());
    }

    @Test
    void shouldAllowAuthorizedUsersOnly() {
        HttpRequest<TravelPeriodDTO> httpRequest = HttpRequest.POST("/period/add", new TravelPeriodDTO())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE);

        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(httpRequest));

        assertThat(httpClientResponseException.getStatus().getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }

    private TravelPeriodDTO createTravelPeriodDTO() {
        TravelPeriodDTO travelPeriodDTO = new TravelPeriodDTO();
        travelPeriodDTO.setStart("2020-02-05");
        travelPeriodDTO.setEnd("2020-02-10");
        travelPeriodDTO.setCountry("Poland");
        travelPeriodDTO.setNote("First trip");
        return travelPeriodDTO;
    }
}