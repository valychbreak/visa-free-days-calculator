package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.service.authentication.UserService;
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

    private final UserService userService;

    public GetTravelPeriodController(UserService userService) {
        this.userService = userService;
    }

    @Get("/period/all")
    public HttpResponse<Collection<TravelPeriod>> getAllUserTravelPeriods(Principal principal) {
        return HttpResponse.ok(new ArrayList<>(userService.getUserTravelPeriods(principal.getName())));
    }
}
