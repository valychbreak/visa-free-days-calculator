package com.valychbreak.calculator.service;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import reactor.core.publisher.Flux;
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

    public Flux<TravelPeriod> findByUser(User user) {
        return asyncRepositoryCallExecutor.async(() -> travelPeriodRepository.findByUser(user))
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<TravelPeriod> save(TravelPeriod travelPeriod) {
        return asyncRepositoryCallExecutor.async(() -> travelPeriodRepository.save(travelPeriod));
    }

    public Mono<TravelPeriod> delete(TravelPeriod travelPeriod) {
        return asyncRepositoryCallExecutor.async(() -> {
            travelPeriodRepository.delete(travelPeriod);
            return travelPeriod;
        });
    }
}
