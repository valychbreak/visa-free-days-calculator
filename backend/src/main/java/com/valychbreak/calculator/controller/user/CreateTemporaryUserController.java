package com.valychbreak.calculator.controller.user;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.service.authentication.TemporaryUserService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller("/api")
@PermitAll
public class CreateTemporaryUserController {

    private TemporaryUserService temporaryUserService;
    private RxHttpClient authenticationClient;

    @Inject
    public CreateTemporaryUserController(TemporaryUserService temporaryUserService,
                                         @Client("/login") RxHttpClient authenticationClient) {
        this.temporaryUserService = temporaryUserService;
        this.authenticationClient = authenticationClient;
    }

    @Get("/user/temporary")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<AccessRefreshToken> createTemporaryUser() {
        User temporaryUser = temporaryUserService.create();

        HttpRequest<UsernamePasswordCredentials> httpRequest = HttpRequest.POST(
                "", new UsernamePasswordCredentials(temporaryUser.getUsername(), temporaryUser.getPassword())
        );

        return authenticationClient.toBlocking().exchange(httpRequest, AccessRefreshToken.class);
    }
}
