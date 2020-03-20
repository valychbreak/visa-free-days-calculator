package com.valychbreak.calculator.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
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
}
