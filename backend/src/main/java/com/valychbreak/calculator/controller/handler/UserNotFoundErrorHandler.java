package com.valychbreak.calculator.controller.handler;

import com.valychbreak.calculator.exception.UserNotFoundException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

@Singleton
@Produces
@Requires(classes = {UserNotFoundErrorHandler.class, ExceptionHandler.class})
public class UserNotFoundErrorHandler implements ExceptionHandler<UserNotFoundException, HttpResponse<Object>> {
    @Override
    public HttpResponse<Object> handle(HttpRequest request, UserNotFoundException exception) {
        return HttpResponse.unauthorized();
    }
}
