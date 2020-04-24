package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.service.TravelPeriodService;
import com.valychbreak.calculator.service.user.UserReactiveService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Flux;

import java.security.Principal;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class GetTravelPeriodController {

    private final UserReactiveService userReactiveService;
    private final TravelPeriodService travelPeriodService;

    public GetTravelPeriodController(UserReactiveService userReactiveService, TravelPeriodService travelPeriodService) {
        this.userReactiveService = userReactiveService;
        this.travelPeriodService = travelPeriodService;
    }

    @Get("/period/all")
    public Flux<TravelPeriod> getAllUserTravelPeriods(Principal principal) {
        return userReactiveService.findUserBy(principal)
                .flatMapMany(travelPeriodService::findByUser);
    }
}
