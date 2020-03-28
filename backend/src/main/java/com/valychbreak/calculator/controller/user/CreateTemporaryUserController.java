package com.valychbreak.calculator.controller.user;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

@Controller("/api")
@PermitAll
public class CreateTemporaryUserController {

    private UserRepository userRepository;

    @Inject
    public CreateTemporaryUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Get("/user/temporary")
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> createTemporaryUser() {
        User user = new User();
        user.setUsername("TemporaryUsername");
        userRepository.save(user);
        return HttpResponse.ok("temporary_token");
    }
}
