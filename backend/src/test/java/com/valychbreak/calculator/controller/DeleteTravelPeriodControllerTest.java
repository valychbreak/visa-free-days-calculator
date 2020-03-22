package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class DeleteTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    @Test
    void shouldDeleteTravelPeriod() {
        TravelPeriod travelPeriod = TravelPeriod.builder()
                .start(LocalDate.of(2020, 3, 15))
                .end(LocalDate.of(2020, 3, 20))
                .country("Poland")
                .build();

        travelPeriodRepository.save(travelPeriod);

        MutableHttpRequest<Object> httpRequest = HttpRequest.DELETE(String.format("/period/%s/delete", travelPeriod.getId()));

        HttpResponse<Object> httpResponse = client.toBlocking().exchange(httpRequest);
        assertThat(httpResponse.code()).isEqualTo(HttpStatus.OK.getCode());

        assertThat(travelPeriodRepository.findById(travelPeriod.getId())).isEmpty();
    }

    @Test
    void shouldReturnNotFoundErrorWhenTravelPeriodIdDoesNotExist() {
        MutableHttpRequest<Object> httpRequest = HttpRequest.DELETE("/period/10000/delete");

        HttpClientResponseException thrownException = Assertions.assertThrows(
                HttpClientResponseException.class, () -> client.toBlocking().exchange(httpRequest)
        );

        assertThat(thrownException.getStatus().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }
}