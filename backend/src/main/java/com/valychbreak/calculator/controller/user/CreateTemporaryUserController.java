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

    private final TemporaryUserService temporaryUserService;
    private final AuthenticationClient authenticationClient;

    @Inject
    public CreateTemporaryUserController(TemporaryUserService temporaryUserService,
                                         AuthenticationClient authenticationClient) {
        this.temporaryUserService = temporaryUserService;
        this.authenticationClient = authenticationClient;
    }

    @Get("/user/temporary")
    @Produces(MediaType.APPLICATION_JSON)
    public Single<? extends HttpResponse<AccessRefreshToken>> createTemporaryUser() {
        return Single.fromCallable(temporaryUserService::create)
                .flatMap(temporaryUser -> authenticationClient.requestToken(temporaryUser.getUsername(), temporaryUser.getPassword()))
                .map(HttpResponse::ok);
    }
}
