package com.valychbreak.calculator.controller.user;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.service.authentication.TemporaryUserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.token.jwt.render.AccessRefreshToken;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller("/api")
@PermitAll
public class CreateTemporaryUserController {

    @Inject
    private TemporaryUserService temporaryUserService;

    @Get("/user/temporary")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<AccessRefreshToken> createTemporaryUser() {
        User temporaryUser = temporaryUserService.create();
        return HttpResponse.ok(new AccessRefreshToken("temp_token", "temp_refresh_token", "bearer", 1000));
    }
}
