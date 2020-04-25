package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.TravelPeriod.TravelPeriodBuilder;
import com.valychbreak.calculator.domain.dto.TravelPeriodDTO;
import com.valychbreak.calculator.service.TravelPeriodService;
import com.valychbreak.calculator.service.user.UserService;
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

    private final UserService userService;
    private final TravelPeriodService travelPeriodService;

    @Inject
    public AddTravelPeriodController(UserService userService, TravelPeriodService travelPeriodService) {
        this.userService = userService;
        this.travelPeriodService = travelPeriodService;
    }

    @Post(value = "/period/add")
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Mono<TravelPeriodDTO> addNewPeriod(@Body TravelPeriodDTO travelPeriodDTO, Principal principal) {
        return Mono.just(TravelPeriod.from(travelPeriodDTO))
                .zipWith(userService.findUserBy(principal), TravelPeriodBuilder::user)
                .map(TravelPeriodBuilder::build)
                .flatMap(travelPeriodService::save)
                .map(TravelPeriodDTO::new);
    }

}
