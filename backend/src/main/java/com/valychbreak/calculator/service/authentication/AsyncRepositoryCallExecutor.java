package com.valychbreak.calculator.service.authentication;

import io.micronaut.context.annotation.Value;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.inject.Singleton;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

@Singleton
public class AsyncRepositoryCallExecutor {
    private final Scheduler jdbcPoolScheduler;

    public AsyncRepositoryCallExecutor(@Value("${datasources.default.maximumPoolSize}") Integer maximumPoolSize) {
        this.jdbcPoolScheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(maximumPoolSize));
    }

    public <T> Mono<T> async(Callable<T> callable) {
        return Mono.fromCallable(callable)
                .subscribeOn(jdbcPoolScheduler);
    }
}
