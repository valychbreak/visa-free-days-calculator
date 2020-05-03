package com.valychbreak.calculator.service.authentication;

import com.valychbreak.calculator.service.user.UserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.*;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;

import static io.micronaut.security.authentication.AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH;

@Singleton
public class UserCredentialsAuthenticationProvider implements HttpAuthenticationProvider {

    private static final AuthenticationFailed FAILED_AUTHENTICATION = new AuthenticationFailed(CREDENTIALS_DO_NOT_MATCH);

    private final UserService userService;

    @Inject
    public UserCredentialsAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> request, AuthenticationRequest<?, ?> authenticationRequest) {
        String username = (String) authenticationRequest.getIdentity();
        String password = (String) authenticationRequest.getSecret();

        return userService.findUserBy(username, password)
                .map(user -> (AuthenticationResponse) new UserDetails(user.getUsername(), Collections.emptyList()))
                .onErrorReturn(FAILED_AUTHENTICATION);
    }
}
