package com.valychbreak.calculator.repository;

import com.valychbreak.calculator.domain.TravelPeriod;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Singleton
public class TravelPeriodRepository {
    private final List<TravelPeriod> travelPeriods;
    private long nextId = 1;

    public TravelPeriodRepository() {
        this.travelPeriods = new ArrayList<>();

        this.save(TravelPeriod.builder()
                .id(1L)
                .start(LocalDate.of(2020, 3, 15).minusDays(4))
                .end(LocalDate.of(2020, 3, 15))
                .country("Poland")
                .note("Some note")
                .build()
        );
    }

    public Collection<TravelPeriod> findAll() {
        return travelPeriods;
    }

    public TravelPeriod save(TravelPeriod travelPeriod) {
        travelPeriod.setId(nextId++);
        travelPeriods.add(travelPeriod);

        return travelPeriod;
    }
}
