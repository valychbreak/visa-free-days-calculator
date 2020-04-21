package com.valychbreak.calculator.service;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.service.authentication.AsyncRepositoryCallExecutor;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;

@Singleton
public class TravelPeriodService {
    private final TravelPeriodRepository travelPeriodRepository;
    private final AsyncRepositoryCallExecutor asyncRepositoryCallExecutor;

    public TravelPeriodService(TravelPeriodRepository travelPeriodRepository, AsyncRepositoryCallExecutor asyncRepositoryCallExecutor) {
        this.travelPeriodRepository = travelPeriodRepository;
        this.asyncRepositoryCallExecutor = asyncRepositoryCallExecutor;
    }

    public Mono<TravelPeriod> findTravelPeriodById(Long id) {
        return asyncRepositoryCallExecutor.async(() -> travelPeriodRepository.findById(id))
                .flatMap(Mono::justOrEmpty);
    }
}
