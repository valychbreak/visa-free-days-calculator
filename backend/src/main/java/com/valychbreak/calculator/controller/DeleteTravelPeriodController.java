package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.service.TravelPeriodService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.security.Principal;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class DeleteTravelPeriodController {

    private final TravelPeriodService travelPeriodService;

    @Inject
    public DeleteTravelPeriodController(TravelPeriodService travelPeriodService) {
        this.travelPeriodService = travelPeriodService;
    }

    @Delete("/period/{id}/delete")
    public Mono<? extends HttpResponse<Object>> deleteTravelPeriod(Long id, Principal principal) {
        return travelPeriodService.findTravelPeriodById(id)
                .filter(travelPeriod -> hasRightsToRemove(principal, travelPeriod))
                .flatMap(travelPeriodService::delete)
                .map(travelPeriod -> HttpResponse.ok())
                .switchIfEmpty(Mono.just(HttpResponse.notFound()));
    }

    private boolean hasRightsToRemove(Principal principal, TravelPeriod travelPeriod) {
        return principal.getName().equals(travelPeriod.getUser().getUsername());
    }
}
