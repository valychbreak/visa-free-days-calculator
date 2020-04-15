package com.valychbreak.calculator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@JsonDeserialize(builder = TravelPeriod.TravelPeriodBuilder.class)
@JsonIgnoreProperties("user")
@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class TravelPeriod {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private String country;
    private String note;
    @ManyToOne
    private User user;

    @Builder
    private TravelPeriod(Long id, LocalDate start, LocalDate end, String country, String note, User user) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.country = country;
        this.note = note;
        this.user = user;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class TravelPeriodBuilder {

    }
}
