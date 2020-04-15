package com.valychbreak.calculator.utils;

import io.micronaut.transaction.SynchronousTransactionManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.util.function.Supplier;

@Singleton
public class ControllerTestDriver {

    @Inject
    private SynchronousTransactionManager<Connection> transactionManager;

    public <T> T performTransactionalWrite(Supplier<T> function) {
        return transactionManager.executeWrite(status -> function.get());
    }
}
