package com.valychbreak.calculator.controller;

import com.fasterxml.jackson.databind.SerializationFeature;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.jackson.annotation.JacksonFeatures;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@MicronautTest
class DateObjectControllerTest {
    @Inject
    @Client("/api")
    private RxHttpClient client;

    @Test
    void shouldSuccessfullyExecute() {
        DateObjectController.MyClass myClass = new DateObjectController.MyClass();
        myClass.setName("Some name");
        myClass.setDate(ZonedDateTime.now().toLocalDate());

        MutableHttpRequest<DateObjectController.MyClass> httpRequest = HttpRequest.POST("/date", myClass)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_TYPE);

        HttpResponse<Object> exchange = client.toBlocking().exchange(httpRequest);
        Assertions.assertEquals(200, exchange.code());
    }
}