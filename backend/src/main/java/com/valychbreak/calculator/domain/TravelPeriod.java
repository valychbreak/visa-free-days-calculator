package com.valychbreak.calculator.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@JsonDeserialize(builder = TravelPeriod.TravelPeriodBuilder.class)
@Getter
@Setter
public class TravelPeriod {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private String country;
    private String note;

    @Builder
    private TravelPeriod(Long id, LocalDate start, LocalDate end, String country, String note) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.country = country;
        this.note = note;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class TravelPeriodBuilder {

    }
}
