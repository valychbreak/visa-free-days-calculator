package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import java.util.Collection;

@Controller("/api")
@PermitAll
public class GetTravelPeriodController {

    private final TravelPeriodRepository travelPeriodRepository;

    @Inject
    public GetTravelPeriodController(TravelPeriodRepository travelPeriodRepository) {
        this.travelPeriodRepository = travelPeriodRepository;
    }

    @Get("/period/all")
    public HttpResponse<Collection<TravelPeriod>> getAllPeriods() {

        return HttpResponse.ok(travelPeriodRepository.findAll());
    }
}
