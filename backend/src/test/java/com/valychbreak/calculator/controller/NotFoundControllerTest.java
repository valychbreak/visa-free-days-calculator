package com.valychbreak.calculator.controller;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
class NotFoundControllerTest {

    @Inject
    @Client("/")
    private RxHttpClient client;

    @Inject
    private ResourceResolver resourceResolver;

    @Test
    void shouldReturnNotFoundErrorForApiRequests() {
        HttpRequest<?> notExistingApiRequest = HttpRequest.GET("/api/notExists");

        HttpClientResponseException clientResponseException = Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().retrieve(notExistingApiRequest));

        assertThat(clientResponseException.getStatus().getCode()).isEqualTo(HttpStatus.NOT_FOUND.getCode());
    }

    @Test
    @Disabled("At the point of running tests, there's no client resources are build")
    void shouldForwardPathResolvingToClient() throws IOException {
        HttpRequest<?> notExistingApiRequest = HttpRequest.GET("/some-not-existing-path");
        String response = client.toBlocking().retrieve(notExistingApiRequest);

        URL clientIndexFile = resourceResolver.getResource("classpath:public/index.html").orElseThrow();
        String expectedResponse = Files.lines(Path.of(clientIndexFile.getPath()))
                .collect(Collectors.joining());

        assertThat(response).isEqualTo(expectedResponse);
    }
}