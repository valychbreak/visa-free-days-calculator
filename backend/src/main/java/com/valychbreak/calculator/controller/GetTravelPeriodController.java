package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.service.TravelPeriodService;
import com.valychbreak.calculator.service.user.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Flux;

import java.security.Principal;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class GetTravelPeriodController {

    private final UserService userService;
    private final TravelPeriodService travelPeriodService;

    public GetTravelPeriodController(UserService userService, TravelPeriodService travelPeriodService) {
        this.userService = userService;
        this.travelPeriodService = travelPeriodService;
    }

    @Get("/period/all")
    public Flux<TravelPeriod> getAllUserTravelPeriods(Principal principal) {
        return userService.findUserBy(principal)
                .flatMapMany(travelPeriodService::findByUser);
    }
}
