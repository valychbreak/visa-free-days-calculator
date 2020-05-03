package com.valychbreak.calculator.utils;

import com.valychbreak.calculator.controller.AuthenticationClient;
import com.valychbreak.calculator.domain.User;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;

import javax.inject.Singleton;

@Requires(env = Environment.TEST)
@Singleton
public class TestAuthTokenProvider {

    private final AuthenticationClient authClient;

    public TestAuthTokenProvider(AuthenticationClient authenticationClient) {
        this.authClient = authenticationClient;
    }

    public String getAccessToken(User user) {
        return getToken(user).getAccessToken();
    }

    public AccessRefreshToken getToken(User user) {
        return authClient.requestToken(user.getUsername(), user.getPassword())
                .blockingGet();
    }
}
