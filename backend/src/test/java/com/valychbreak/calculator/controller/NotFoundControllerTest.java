package com.valychbreak.calculator.controller;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.server.types.files.StreamedFile;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
    void shouldForwardPathResolvingToClient() throws IOException {
        HttpRequest<?> notExistingApiRequest = HttpRequest.GET("/some-path");

        HttpResponse<StreamedFile> response = client.toBlocking().exchange(notExistingApiRequest, StreamedFile.class);
        assertThat(response.code()).isEqualTo(HttpStatus.OK.getCode());

        URL clientIndexFile = resourceResolver.getResource("classpath:public/index.html").orElseThrow();
        try (InputStream expectedFileContent = clientIndexFile.openStream();
             InputStream actualResponseContent = response.getBody().orElseThrow().getInputStream()) {

            assertThat(actualResponseContent).isEqualTo(expectedFileContent);
        }
    }
}