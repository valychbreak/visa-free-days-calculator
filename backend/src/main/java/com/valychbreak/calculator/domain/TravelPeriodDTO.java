package com.valychbreak.calculator.domain;

import io.micronaut.core.annotation.Introspected;
import lombok.Getter;
import lombok.Setter;


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
}
