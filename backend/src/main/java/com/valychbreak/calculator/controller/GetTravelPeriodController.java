package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.service.authentication.UserService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import io.reactivex.Flowable;

import java.security.Principal;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class GetTravelPeriodController {

    private final UserService userService;

    public GetTravelPeriodController(UserService userService) {
        this.userService = userService;
    }

    @Get("/period/all")
    public Flowable<TravelPeriod> getAllUserTravelPeriods(Principal principal) {
        return Flowable.fromIterable(userService.getUserTravelPeriods(principal.getName()));
    }
}
