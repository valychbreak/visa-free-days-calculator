package com.valychbreak.calculator.service;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.utils.TestUtils;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.test.StepVerifier;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class TravelPeriodServiceTest {

    @Inject
    private TravelPeriodService travelPeriodService;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    @Named("jdbcScheduler")
    private Scheduler scheduler;

    private TravelPeriod travelPeriod;
    private User travelPeriodUser;

    @BeforeEach
    void setUp() {
        travelPeriodUser = TestUtils.createUser("testTravelPeriodService");

        travelPeriod = TravelPeriod.builder()
                .start(LocalDate.of(2020, 1, 1))
                .end(LocalDate.of(2020, 2, 1))
                .country("Spain")
                .note("First trip")
                .user(travelPeriodUser)
                .build();
    }

    @Test
    void shouldFindById() {
        // given
        var create = createTravelPeriodMono(travelPeriod);

        // when
        var find = Mono.from(create)
                .flatMap(createdTravelPeriod -> travelPeriodService.findTravelPeriodById(createdTravelPeriod.getId()));

        // then
        StepVerifier.create(find)
                .expectNext(travelPeriod)
                .verifyComplete();
    }

    @Test
    void shouldFindByUser() {
        // given
        var create = createTravelPeriodMono(travelPeriod);

        // when
        var find = Mono.from(create)
                .flatMapMany(createdTravelPeriod -> travelPeriodService.findByUser(travelPeriodUser));

        // then
        StepVerifier.create(find)
                .expectNext(travelPeriod)
                .verifyComplete();
    }

    @Test
    void shouldSave() {
        // when
        var createUser = createUserMono();
        var save = Mono.fromRunnable(() -> travelPeriodRepository.deleteAll())
                .then(createUser)
                .then(travelPeriodService.save(travelPeriod))
                .subscribeOn(scheduler);

        // then
        StepVerifier.create(save)
                .assertNext(savedTravelPeriod ->
                        assertThat(travelPeriod.getId()).isNotNull()
                ).verifyComplete();
    }

    @Test
    void shouldDelete() {
        // given
        var create = createTravelPeriodMono(travelPeriod);

        // when
        var delete = Mono.from(travelPeriodService.delete(travelPeriod));

        // then
        var compositeFind = Mono.from(create)
                .then(delete)
                .map(removedTravelPeriod -> travelPeriodRepository.findById(removedTravelPeriod.getId()))
                .flatMap(Mono::justOrEmpty);

        StepVerifier.create(compositeFind)
                .expectComplete()
                .verify();
    }

    private Mono<TravelPeriod> createTravelPeriodMono(TravelPeriod travelPeriod) {
        var createUser = createUserMono();
        return Mono.from(Mono.fromRunnable(() -> travelPeriodRepository.deleteAll()))
                .then(createUser)
                .then(Mono.fromCallable(() -> travelPeriodRepository.save(travelPeriod)))
                .subscribeOn(scheduler);
    }

    private Mono<Object> createUserMono() {
        return Mono.fromRunnable(() -> userRepository.deleteAll())
                    .then(Mono.fromRunnable(() -> userRepository.save(travelPeriodUser)));
    }
}