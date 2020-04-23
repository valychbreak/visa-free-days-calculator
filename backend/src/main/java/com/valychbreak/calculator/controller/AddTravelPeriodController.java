package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.TravelPeriod.TravelPeriodBuilder;
import com.valychbreak.calculator.domain.TravelPeriodDTO;
import com.valychbreak.calculator.service.TravelPeriodService;
import com.valychbreak.calculator.service.UserReactiveService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.security.Principal;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class AddTravelPeriodController {

    private final UserReactiveService userReactiveService;
    private final TravelPeriodService travelPeriodService;

    @Inject
    public AddTravelPeriodController(UserReactiveService userReactiveService, TravelPeriodService travelPeriodService) {
        this.userReactiveService = userReactiveService;
        this.travelPeriodService = travelPeriodService;
    }

    @Post(value = "/period/add")
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Mono<TravelPeriodDTO> addNewPeriod(@Body TravelPeriodDTO travelPeriodDTO, Principal principal) {
        return Mono.just(TravelPeriod.from(travelPeriodDTO))
                .zipWith(userReactiveService.findUserBy(principal), TravelPeriodBuilder::user)
                .map(TravelPeriodBuilder::build)
                .flatMap(travelPeriodService::save)
                .map(TravelPeriodDTO::new);
    }

}
