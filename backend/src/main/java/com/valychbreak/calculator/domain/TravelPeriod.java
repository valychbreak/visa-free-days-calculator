package com.valychbreak.calculator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.valychbreak.calculator.domain.dto.TravelPeriodDTO;
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
    @SequenceGenerator(name = "travel_period_id_gen", sequenceName = "travel_period_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_period_id_gen")
    private Long id;

    @Column(nullable = false)
    private LocalDate start;

    @Column(name = "\"end\"", nullable = false)
    private LocalDate end;

    @Column(nullable = false)
    private String country;

    @Column
    private String note;

    @ManyToOne
    private User user;

    public static TravelPeriodBuilder from(TravelPeriodDTO travelPeriodDTO) {
        return TravelPeriod.builder()
                .start(LocalDate.parse(travelPeriodDTO.getStart()))
                .end(LocalDate.parse(travelPeriodDTO.getEnd()))
                .country(travelPeriodDTO.getCountry())
                .note(travelPeriodDTO.getNote());
    }

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
