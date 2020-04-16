package com.valychbreak.calculator.utils;

import com.valychbreak.calculator.repository.TravelPeriodRepository;
import com.valychbreak.calculator.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DatabaseHelper {
    @Inject
    private UserRepository userRepository;

    @Inject
    private TravelPeriodRepository travelPeriodRepository;

    public void cleanupEverything() {
        travelPeriodRepository.deleteAll();
        userRepository.deleteAll();
    }
}
