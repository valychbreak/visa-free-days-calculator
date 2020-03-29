package com.valychbreak.calculator.utils;

import com.valychbreak.calculator.domain.User;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;

import javax.inject.Singleton;
import java.util.Optional;

@Requires(env = Environment.TEST)
@Singleton
public class TestAuthTokenProvider {

    private RxHttpClient authClient;

    public TestAuthTokenProvider(@Client("/login") RxHttpClient authClient) {
        this.authClient = authClient;
    }

    public String getToken(User user) {
        HttpRequest<UsernamePasswordCredentials> httpRequest = HttpRequest.POST(
                "", new UsernamePasswordCredentials(user.getUsername(), user.getPassword())
        );

        return Optional.ofNullable(authClient.toBlocking()
                .exchange(httpRequest, AccessRefreshToken.class)
                .body())
                .orElseThrow()
                .getAccessToken();
    }
}
