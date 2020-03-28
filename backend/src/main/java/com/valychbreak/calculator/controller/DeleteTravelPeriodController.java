package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;

import javax.annotation.security.PermitAll;
import java.util.Optional;

@Controller("/api")
@PermitAll
public class DeleteTravelPeriodController {

    private TravelPeriodRepository travelPeriodRepository;

    public DeleteTravelPeriodController(TravelPeriodRepository travelPeriodRepository) {
        this.travelPeriodRepository = travelPeriodRepository;
    }

    @Delete("/period/{id}/delete")
    public HttpResponse<String> deleteTravelPeriod(Long id) {
        Optional<TravelPeriod> travelPeriod = travelPeriodRepository.findById(id);

        if (travelPeriod.isEmpty()) {
            return HttpResponse.notFound();
        }

        travelPeriodRepository.delete(travelPeriod.get());
        return HttpResponse.ok();
    }
}
