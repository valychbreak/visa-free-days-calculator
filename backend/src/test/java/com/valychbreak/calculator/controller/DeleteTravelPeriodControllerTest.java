package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.utils.TestAuthTokenProvider;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;

import static com.valychbreak.calculator.utils.TestUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest(transactional = false)
class DeleteTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TestAuthTokenProvider authTokenProvider;

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser("deleteTravelPeriodUser");
        userRepository.save(user);
    }

    @Test
    void shouldDeleteTravelPeriod() {
        TravelPeriod travelPeriod = TravelPeriod.builder()
                .start(LocalDate.of(2020, 3, 15))
                .end(LocalDate.of(2020, 3, 20))
                .country("Poland")
                .user(user)
                .build();

        travelPeriodRepository.save(travelPeriod);

        HttpRequest<Object> httpRequest = HttpRequest.DELETE(String.format("/period/%s/delete", travelPeriod.getId()))
                .bearerAuth(authTokenProvider.getToken(user));

        HttpResponse<Object> httpResponse = client.toBlocking().exchange(httpRequest);
        assertThat(httpResponse.code()).isEqualTo(HttpStatus.OK.getCode());

        assertThat(travelPeriodRepository.findById(travelPeriod.getId())).isEmpty();
    }

    @Test
    void shouldReturnNotFoundErrorWhenTravelPeriodIdDoesNotExist() {
        MutableHttpRequest<Object> httpRequest = HttpRequest.DELETE("/period/10000/delete")
                .bearerAuth(authTokenProvider.getToken(user));

        HttpClientResponseException thrownException = assertThrows(
                HttpClientResponseException.class, () -> client.toBlocking().exchange(httpRequest)
        );

        assertThat(thrownException.getStatus().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    void shouldReturnNotFoundErrorWhenUserDoesNotOwnTravelPeriod() {
        User anotherUser = createUser("anotherUserDeletePeriod");
        userRepository.save(anotherUser);

        TravelPeriod travelPeriod = TravelPeriod.builder()
                .start(LocalDate.of(2020, 3, 15))
                .end(LocalDate.of(2020, 3, 20))
                .country("Poland")
                .user(anotherUser)
                .build();

        travelPeriodRepository.save(travelPeriod);

        HttpRequest<Object> httpRequest = HttpRequest.DELETE(String.format("/period/%s/delete", travelPeriod.getId()))
                .bearerAuth(authTokenProvider.getToken(user));

        HttpClientResponseException thrownException = assertThrows(
                HttpClientResponseException.class, () -> client.toBlocking().exchange(httpRequest)
        );

        assertThat(thrownException.getStatus().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    void shouldNotAllowNotAuthenticatedUser() {

        HttpRequest<Object> httpRequest = HttpRequest.DELETE(String.format("/period/%s/delete", 100L));

        HttpClientResponseException exception = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(httpRequest));

        assertThat(exception.getResponse().code()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }
}