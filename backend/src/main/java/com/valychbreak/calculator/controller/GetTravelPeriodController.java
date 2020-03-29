package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Collection;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class GetTravelPeriodController {

    private final UserRepository userRepository;

    @Inject
    public GetTravelPeriodController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Get("/period/all")
    public HttpResponse<Collection<TravelPeriod>> getAllUserTravelPeriods(Principal principal) {
        return HttpResponse.ok(
                userRepository.findByUsername(principal.getName())
                        .orElseThrow()
                        .getTravelPeriods()
        );
    }
}
