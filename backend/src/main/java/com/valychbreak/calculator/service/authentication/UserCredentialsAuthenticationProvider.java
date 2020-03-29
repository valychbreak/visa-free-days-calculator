package com.valychbreak.calculator.service.authentication;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Optional;

import static io.micronaut.security.authentication.AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH;

@Singleton
public class UserCredentialsAuthenticationProvider implements AuthenticationProvider {

    private UserRepository userRepository;

    @Inject
    public UserCredentialsAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {
        Optional<User> user = userRepository.findByUsername((String) authenticationRequest.getIdentity())
                .filter(foundUser -> authenticationRequest.getSecret().equals(foundUser.getPassword()));
        if (user.isPresent()) {
            return Flowable.just(new UserDetails(user.get().getUsername(), new ArrayList<>()));
        }

        return Flowable.just(new AuthenticationFailed(CREDENTIALS_DO_NOT_MATCH));
    }
}
