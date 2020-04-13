package com.valychbreak.calculator.utils;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.transaction.SynchronousTransactionManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;

import static com.valychbreak.calculator.utils.TestUtils.createUser;

@Singleton
public class ControllerTestDriver {

    @Inject
    private SynchronousTransactionManager<Connection> transactionManager;

    @Inject
    private UserRepository userRepository;


    public User saveUser(final User user) {
        return transactionManager.executeWrite(status -> {
            return userRepository.save(user);
        });
    }
}
