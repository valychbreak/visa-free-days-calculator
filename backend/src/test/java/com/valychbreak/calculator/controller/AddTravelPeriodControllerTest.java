package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriodDTO;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class AddTravelPeriodControllerTest {

    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    @Test
    void shouldCreateNewPeriod() {
        TravelPeriodDTO travelPeriodDTO = new TravelPeriodDTO();
        travelPeriodDTO.setStart("2020-02-05");
        travelPeriodDTO.setEnd("2020-02-10");
        travelPeriodDTO.setCountry("Poland");
        travelPeriodDTO.setNote("First trip");

        MutableHttpRequest<TravelPeriodDTO> httpRequest = HttpRequest.POST("/period/add", travelPeriodDTO)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE);

        HttpResponse<TravelPeriodDTO> response = client.toBlocking()
                .exchange(httpRequest, TravelPeriodDTO.class);

        assertThat(response.body().getId()).isNotNull();
        assertThat(response.body()).isEqualToIgnoringGivenFields(travelPeriodDTO, "id");

        assertThat(travelPeriodRepository.findAll()).extracting("id").contains(response.body().getId());
    }
}