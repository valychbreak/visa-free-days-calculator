package com.valychbreak.calculator.service.authentication;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.Callable;

@Singleton
public class AsyncRepositoryCallExecutor {
    private final Scheduler jdbcPoolScheduler;

    public AsyncRepositoryCallExecutor(@Named("jdbcScheduler") Scheduler jdbcPoolScheduler) {
        this.jdbcPoolScheduler = jdbcPoolScheduler;
    }

    public <T> Mono<T> async(Callable<T> callable) {
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcPoolScheduler);
    }
}
