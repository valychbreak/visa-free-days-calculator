package com.valychbreak.calculator.service.authentication;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.UserRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Singleton
public class TemporaryUserService {

    @Inject
    private UserRepository userRepository;

    public TemporaryUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create() {
        User user = new User();
        user.setUsername(randomAlphanumeric(32));
        user.setPassword(randomAlphanumeric(32));
        user.setEmail(randomAlphanumeric(32));
        user.setTemporary(true);
        user.setTravelPeriods(new ArrayList<>());
        userRepository.save(user);
        return user;
    }
}
