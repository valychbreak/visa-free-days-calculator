package com.valychbreak.calculator.controller;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.utils.TestAuthTokenProvider;
import com.valychbreak.calculator.utils.TestUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.token.jwt.endpoints.TokenRefreshRequest;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(transactional = false)
public class RefreshTokenEndpointTest {

    @Inject
    @Client("/")
    private RxHttpClient client;

    @Inject
    private TestAuthTokenProvider tokenProvider;

    @Inject
    private UserRepository userRepository;

    @Test
    void shouldRefreshToken() throws InterruptedException {
        // given
        User user = TestUtils.createUser("refreshTokenUser");
        userRepository.save(user);

        AccessRefreshToken accessRefreshToken = tokenProvider.getToken(user);

        // when
        // waiting for second to pass in order to have different expiration date for token
        Thread.sleep(1000);
        MutableHttpRequest<TokenRefreshRequest> refreshTokenRequest = HttpRequest.POST("/oauth/access_token", new TokenRefreshRequest("refresh_token", accessRefreshToken.getRefreshToken()));
        HttpResponse<AccessRefreshToken> response = client.toBlocking().exchange(refreshTokenRequest, AccessRefreshToken.class);

        // then
        AccessRefreshToken refreshedToken = response.body();
        assertThat(refreshedToken).isNotNull();
        assertThat(refreshedToken.getAccessToken()).isNotEqualTo(accessRefreshToken.getAccessToken());
    }
}
