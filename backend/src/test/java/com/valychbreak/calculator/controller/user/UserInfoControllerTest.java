package com.valychbreak.calculator.controller.user;


import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.domain.dto.UserDto;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.utils.TestAuthTokenProvider;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static com.valychbreak.calculator.utils.TestUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class UserInfoControllerTest {
    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private UserRepository userRepository;

    @Inject
    private TestAuthTokenProvider authTokenProvider;

    @Test
    void shouldReturnUserInfo() {
        User testUser = createUser("testTravelPeriods");
        userRepository.save(testUser);

        HttpRequest<Object> httpRequest = HttpRequest.GET("/user/info")
                .bearerAuth(authTokenProvider.getToken(testUser));

        HttpResponse<UserDto> response = client.toBlocking().exchange(httpRequest, UserDto.class);
        assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());
        assertThat(response.getBody()).isPresent();

        UserDto user = response.getBody().get();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getUsername()).isNotEmpty();
        assertThat(user.getEmail()).isNotEmpty();
    }

    @Test
    void shouldAllowAuthorizedUsersOnly() {

        HttpRequest<Object> httpRequest = HttpRequest.GET("/user/info");

        HttpClientResponseException httpClientResponseException = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(httpRequest));

        assertThat(httpClientResponseException.getStatus().getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.getCode());
    }
}