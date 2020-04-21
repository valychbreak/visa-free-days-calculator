package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.service.authentication.AsyncRepositoryCallExecutor;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Optional;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class GetTravelPeriodController {

    private final AsyncRepositoryCallExecutor asyncRepositoryCallExecutor;
    private final UserRepository userRepository;
    private final TravelPeriodRepository travelPeriodRepository;

    public GetTravelPeriodController(AsyncRepositoryCallExecutor asyncRepositoryCallExecutor,
                                     UserRepository userRepository,
                                     TravelPeriodRepository travelPeriodRepository) {
        this.asyncRepositoryCallExecutor = asyncRepositoryCallExecutor;
        this.userRepository = userRepository;
        this.travelPeriodRepository = travelPeriodRepository;
    }

    @Get("/period/all")
    public Flux<TravelPeriod> getAllUserTravelPeriods(Principal principal) {
        return asyncRepositoryCallExecutor.async(() -> userRepository.findByUsername(principal.getName()))
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.error(UserNotFoundException::new))
                .map(travelPeriodRepository::findByUser)
                .flatMapMany(Flux::fromIterable);
    }
}
