package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.service.authentication.AsyncRepositoryCallExecutor;
import com.valychbreak.calculator.service.authentication.UserService;
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
    private final AsyncRepositoryCallExecutor asyncRepositoryCallExecutor;

    public GetTravelPeriodController(UserService userService,
                                     AsyncRepositoryCallExecutor asyncRepositoryCallExecutor) {
        this.userService = userService;
        this.asyncRepositoryCallExecutor = asyncRepositoryCallExecutor;
    }

    @Get("/period/all")
    public Flux<TravelPeriod> getAllUserTravelPeriods(Principal principal) {
        return asyncRepositoryCallExecutor.async(() -> userService.getUserTravelPeriods(principal.getName()))
                .flatMapMany(Flux::fromIterable);
    }
}
