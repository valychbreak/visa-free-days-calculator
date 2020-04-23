package com.valychbreak.calculator.configuration;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Factory
public class ExecutorServiceConfig {

    @Singleton
    @Named("jdbcIOExecutorService")
    public ExecutorService jdbcIOExecutorService(@Value("${datasources.default.maximumPoolSize}") Integer maximumPoolSize) {
        return Executors.newFixedThreadPool(maximumPoolSize);
    }

    @Singleton
    @Named("jdbcScheduler")
    public Scheduler jdbcScheduler(@Named("jdbcIOExecutorService") ExecutorService jdbcIOExecutorService) {
        return Schedulers.fromExecutor(jdbcIOExecutorService);
    }
}
