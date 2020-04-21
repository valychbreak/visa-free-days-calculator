package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.service.authentication.AsyncRepositoryCallExecutor;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class DeleteTravelPeriodController {

    private final TravelPeriodRepository travelPeriodRepository;
    private final UserRepository userRepository;
    private final AsyncRepositoryCallExecutor asyncRepositoryCallExecutor;

    @Inject
    public DeleteTravelPeriodController(TravelPeriodRepository travelPeriodRepository, UserRepository userRepository, AsyncRepositoryCallExecutor asyncRepositoryCallExecutor) {
        this.travelPeriodRepository = travelPeriodRepository;
        this.userRepository = userRepository;
        this.asyncRepositoryCallExecutor = asyncRepositoryCallExecutor;
    }

    @Delete("/period/{id}/delete")
    public Mono<? extends HttpResponse<Object>> deleteTravelPeriod(Long id, Principal principal) {
        return asyncRepositoryCallExecutor.async(() -> travelPeriodRepository.findById(id))
                .flatMap(Mono::justOrEmpty)
                .filter(travelPeriod -> hasRightsToRemove(principal, travelPeriod))
                .doOnNext(travelPeriodRepository::delete)
                .map(travelPeriod -> HttpResponse.ok())
                .switchIfEmpty(Mono.just(HttpResponse.notFound()));
    }

    private boolean hasRightsToRemove(Principal principal, TravelPeriod travelPeriod) {
        return principal.getName().equals(travelPeriod.getUser().getUsername());
    }
}
