package com.valychbreak.calculator.controller.user;

import com.valychbreak.calculator.controller.AuthenticationClient;
import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.service.authentication.TemporaryUserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;
import io.reactivex.Single;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller("/api")
@PermitAll
public class CreateTemporaryUserController {

    private TemporaryUserService temporaryUserService;
    private AuthenticationClient authenticationClient;

    @Inject
    public CreateTemporaryUserController(TemporaryUserService temporaryUserService,
                                         AuthenticationClient authenticationClient) {
        this.temporaryUserService = temporaryUserService;
        this.authenticationClient = authenticationClient;
    }

    @Get("/user/temporary")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<HttpResponse<AccessRefreshToken>> createTemporaryUser() {
        User temporaryUser = temporaryUserService.create();
        return authenticationClient.requestToken(temporaryUser.getUsername(), temporaryUser.getPassword())
                .map(HttpResponse::ok);
    }
}
