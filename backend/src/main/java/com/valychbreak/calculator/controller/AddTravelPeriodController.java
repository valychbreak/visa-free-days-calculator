package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.TravelPeriod.TravelPeriodBuilder;
import com.valychbreak.calculator.domain.TravelPeriodDTO;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.service.authentication.AsyncRepositoryCallExecutor;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.security.Principal;
import java.time.format.DateTimeFormatter;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class AddTravelPeriodController {

    private final TravelPeriodRepository travelPeriodRepository;
    private final UserRepository userRepository;
    private final AsyncRepositoryCallExecutor asyncRepositoryCallExecutor;

    @Inject
    public AddTravelPeriodController(TravelPeriodRepository travelPeriodRepository,
                                     UserRepository userRepository,
                                     AsyncRepositoryCallExecutor asyncRepositoryCallExecutor) {
        this.travelPeriodRepository = travelPeriodRepository;
        this.userRepository = userRepository;
        this.asyncRepositoryCallExecutor = asyncRepositoryCallExecutor;
    }

    @Post(value = "/period/add")
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Mono<TravelPeriodDTO> addNewPeriod(@Body TravelPeriodDTO travelPeriodDTO, Principal principal) {
        return Mono.just(TravelPeriod.from(travelPeriodDTO))
                .zipWith(asyncRepositoryCallExecutor.async(() -> findUserBy(principal)),
                        TravelPeriodBuilder::user)
                .map(TravelPeriodBuilder::build)
                .doOnNext(travelPeriodRepository::save)
                .map(TravelPeriodDTO::new);
    }

    private User findUserBy(Principal principal) {
        return userRepository.findByUsername(principal.getName())
                .orElseThrow(UserNotFoundException::new);
    }

}
