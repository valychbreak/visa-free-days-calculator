package com.valychbreak.calculator.controller;

import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.reactivex.Single;

import javax.validation.constraints.NotBlank;

@Client("/api/login")
public interface AuthenticationClient extends RxHttpClient {
    @Post
    Single<AccessRefreshToken> requestToken(@NotBlank String username, @NotBlank String password);
}
