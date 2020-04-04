package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class DeleteTravelPeriodController {

    private final TravelPeriodRepository travelPeriodRepository;
    private final UserRepository userRepository;

    @Inject
    public DeleteTravelPeriodController(TravelPeriodRepository travelPeriodRepository, UserRepository userRepository) {
        this.travelPeriodRepository = travelPeriodRepository;
        this.userRepository = userRepository;
    }

    @Delete("/period/{id}/delete")
    public HttpResponse<String> deleteTravelPeriod(Long id, Principal principal) {
        Optional<TravelPeriod> travelPeriodOptional = travelPeriodRepository.findById(id);

        if (travelPeriodOptional.isEmpty()) {
            return HttpResponse.notFound();
        }

        TravelPeriod travelPeriod = travelPeriodOptional.get();
        if (!principal.getName().equals(travelPeriod.getUser().getUsername())) {
            return HttpResponse.notFound();
        }

        travelPeriodRepository.delete(travelPeriod);
        return HttpResponse.ok();
    }
}
