package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static java.time.LocalDate.now;

@Controller("/api")
public class GetTravelPeriodController {

    @Get("/period/all")
    public HttpResponse<Collection<TravelPeriod>> getAllPeriods() {
        TravelPeriod travelPeriod = TravelPeriod.builder()
                .id(1L)
                .start(LocalDate.of(2020, 3, 15).minusDays(4))
                .end(LocalDate.of(2020, 3, 15))
                .country("Poland")
                .note("Some note")
                .build();
        return HttpResponse.ok(List.of(travelPeriod));
    }
}
