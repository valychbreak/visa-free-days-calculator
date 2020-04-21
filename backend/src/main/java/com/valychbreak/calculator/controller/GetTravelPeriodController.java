package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.service.UserReactiveService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class GetTravelPeriodController {

    private final TravelPeriodRepository travelPeriodRepository;
    private final UserReactiveService userReactiveService;

    public GetTravelPeriodController(TravelPeriodRepository travelPeriodRepository, UserReactiveService userReactiveService) {
        this.travelPeriodRepository = travelPeriodRepository;
        this.userReactiveService = userReactiveService;
    }

    @Get("/period/all")
    public Flux<TravelPeriod> getAllUserTravelPeriods(Principal principal) {
        return userReactiveService.findUserBy(principal)
                .map(travelPeriodRepository::findByUser)
                .flatMapMany(Flux::fromIterable);
    }
}
