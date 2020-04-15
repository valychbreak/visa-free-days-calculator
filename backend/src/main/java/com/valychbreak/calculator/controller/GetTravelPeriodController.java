package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class GetTravelPeriodController {

    private final UserRepository userRepository;

    public GetTravelPeriodController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Get("/period/all")
    @Transactional
    public HttpResponse<Collection<TravelPeriod>> getAllUserTravelPeriods(Principal principal) {
        List<TravelPeriod> travelPeriods = userRepository.findByUsername(principal.getName())
                .orElseThrow(UserNotFoundException::new)
                .getTravelPeriods();

        return HttpResponse.ok(new ArrayList<>(travelPeriods));
    }
}
