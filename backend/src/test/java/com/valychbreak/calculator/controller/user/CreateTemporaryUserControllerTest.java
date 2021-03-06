package com.valychbreak.calculator.controller.user;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.utils.DatabaseHelper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class CreateTemporaryUserControllerTest {

    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Inject
    private UserRepository userRepository;

    @Inject
    private DatabaseHelper databaseHelper;

    @BeforeEach
    void setUp() {
        databaseHelper.cleanupEverything();
    }

    @Test
    void shouldCreateTemporaryUser() {
        MutableHttpRequest<String> httpRequest = HttpRequest.GET("/user/temporary");

        HttpResponse<String> response = client.toBlocking().exchange(httpRequest);

        assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());

        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);

        User user = users.iterator().next();
        assertThat(user.getUsername()).isNotEmpty();
        assertThat(user.getPassword()).isNotEmpty();
        assertThat(user.isTemporary()).isTrue();
        assertThat(user.getId()).isNotNull();
        assertThat(user.getTravelPeriods()).isEmpty();
    }

    @Test
    void shouldResponseWithAuthToken() throws JSONException {
        HttpRequest<String> httpRequest = HttpRequest.GET("/user/temporary");

        String responseString = client.toBlocking().retrieve(httpRequest);
        assertThat(responseString).isNotNull();

        JSONObject jsonObject = new JSONObject(responseString);
        assertThat(jsonObject.getString("access_token")).isNotEmpty();
        assertThat(jsonObject.getString("refresh_token")).isNotEmpty();
        assertThat(jsonObject.getString("token_type")).isNotEmpty();
        assertThat(jsonObject.getString("expires_in")).isNotEmpty();
    }
}