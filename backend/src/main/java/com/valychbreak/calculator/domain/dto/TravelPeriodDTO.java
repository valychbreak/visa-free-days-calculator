package com.valychbreak.calculator.domain.dto;

import com.valychbreak.calculator.domain.TravelPeriod;
import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;


@Getter
@Setter
@Introspected
public class TravelPeriodDTO {
    private Long id;

    // Start date as String because of some issue with HttpClient -- LocalDate: Unsupported field (Seconds of Minute)
    private String start;
    private String end;

    private String country;
    private String note;

    public TravelPeriodDTO() {

    }

    public TravelPeriodDTO(TravelPeriod travelPeriod) {
        this.setId(travelPeriod.getId());
        this.setStart(travelPeriod.getStart().format(DateTimeFormatter.ISO_DATE));
        this.setEnd(travelPeriod.getEnd().format(DateTimeFormatter.ISO_DATE));
        this.setCountry(travelPeriod.getCountry());
        this.setNote(travelPeriod.getNote());
    }
}
