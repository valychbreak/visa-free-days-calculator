package com.valychbreak.calculator.controller.user;

import com.valychbreak.calculator.domain.dto.UserDto;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.service.user.UserReactiveService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.security.Principal;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class UserInfoController {

    private final UserReactiveService userService;

    @Inject
    public UserInfoController(UserReactiveService userService) {
        this.userService = userService;
    }

    @Get("/user/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Mono<UserDto> getUserInfo(Principal principal) {
        return userService.findUserBy(principal)
                .flatMap(Mono::justOrEmpty)
                .map(UserDto::from)
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }
}
