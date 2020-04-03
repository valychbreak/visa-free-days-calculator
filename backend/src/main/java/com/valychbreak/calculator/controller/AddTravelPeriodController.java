package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.TravelPeriodDTO;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

import javax.inject.Inject;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class AddTravelPeriodController {

    private final TravelPeriodRepository travelPeriodRepository;
    private final UserRepository userRepository;

    @Inject
    public AddTravelPeriodController(TravelPeriodRepository travelPeriodRepository, UserRepository userRepository) {
        this.travelPeriodRepository = travelPeriodRepository;
        this.userRepository = userRepository;
    }

    @Post(value = "/period/add")
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<TravelPeriodDTO> addNewPeriod(@Body TravelPeriodDTO travelPeriodDTO, Principal principal) {

        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        TravelPeriod builtTravelPeriod = TravelPeriod.builder()
                .start(LocalDate.parse(travelPeriodDTO.getStart()))
                .end(LocalDate.parse(travelPeriodDTO.getEnd()))
                .country(travelPeriodDTO.getCountry())
                .note(travelPeriodDTO.getNote())
                .user(user)
                .build();

        user.getTravelPeriods().add(builtTravelPeriod);

        travelPeriodRepository.save(builtTravelPeriod);

        TravelPeriodDTO savedTravelPeriod = new TravelPeriodDTO();
        savedTravelPeriod.setId(builtTravelPeriod.getId());
        savedTravelPeriod.setStart(builtTravelPeriod.getStart().format(DateTimeFormatter.ISO_DATE));
        savedTravelPeriod.setEnd(builtTravelPeriod.getEnd().format(DateTimeFormatter.ISO_DATE));
        savedTravelPeriod.setCountry(builtTravelPeriod.getCountry());
        savedTravelPeriod.setNote(builtTravelPeriod.getNote());

        return HttpResponse.ok(savedTravelPeriod);
    }
}
