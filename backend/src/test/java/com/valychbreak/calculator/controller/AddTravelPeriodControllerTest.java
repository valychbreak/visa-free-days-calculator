package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.TravelPeriodDTO;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.utils.ControllerTestDriver;
import com.valychbreak.calculator.utils.TestAuthTokenProvider;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Optional;

import static com.valychbreak.calculator.utils.TestUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest(transactional = false)
class AddTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    @Inject
    private TestAuthTokenProvider authTokenProvider;

    @Inject
    private ControllerTestDriver controllerTestDriver;

    @Test
    void shouldCreateNewPeriod() {
        // given
        TravelPeriodDTO travelPeriodDTO = createTravelPeriodDTO();
        User user = controllerTestDriver.saveUser(createUser("createNewPeriodUser"));

        HttpRequest<TravelPeriodDTO> httpRequest = HttpRequest.POST("/period/add", travelPeriodDTO)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .bearerAuth(authTokenProvider.getToken(user));

        // when
        HttpResponse<TravelPeriodDTO> response = client.toBlocking()
                .exchange(httpRequest, TravelPeriodDTO.class);

        // then
        TravelPeriodDTO actualTravelPeriod = response.getBody().orElseThrow();
        assertThat(actualTravelPeriod.getId()).isNotNull();
        assertThat(actualTravelPeriod).isEqualToIgnoringGivenFields(travelPeriodDTO, "id");

        Optional<TravelPeriod> addedTravelPeriod = travelPeriodRepository.findById(actualTravelPeriod.getId());
        assertThat(addedTravelPeriod).isPresent();
        assertThat(addedTravelPeriod.get().getUser().getUsername()).isEqualTo(user.getUsername());
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