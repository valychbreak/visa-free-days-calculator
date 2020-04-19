package com.valychbreak.calculator.service.authentication;

import com.valychbreak.calculator.domain.TravelPeriod;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.UserRepository;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Singleton
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // TODO: remove transactional, and move travel period loading logic to repository
    @Transactional
    public Collection<TravelPeriod> getUserTravelPeriods(String username) {
        List<TravelPeriod> travelPeriods = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new)
                .getTravelPeriods();

        return new ArrayList<>(travelPeriods);
    }
}
