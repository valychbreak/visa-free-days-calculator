package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.TravelPeriodDTO;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller("/api")
public class AddTravelPeriodController {

    private final TravelPeriodRepository travelPeriodRepository;

    @Inject
    public AddTravelPeriodController(TravelPeriodRepository travelPeriodRepository) {
        this.travelPeriodRepository = travelPeriodRepository;
    }

    @Post(value = "/period/add")
    @Consumes(value = MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<TravelPeriodDTO> addNewPeriod(@Body TravelPeriodDTO travelPeriodDTO) {

        TravelPeriod builtTravelPeriod = TravelPeriod.builder()
                .start(LocalDate.parse(travelPeriodDTO.getStart()))
                .end(LocalDate.parse(travelPeriodDTO.getEnd()))
                .country(travelPeriodDTO.getCountry())
                .note(travelPeriodDTO.getNote())
                .build();

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
