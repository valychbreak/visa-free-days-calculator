package com.valychbreak.calculator.controller.user;

import com.valychbreak.calculator.domain.dto.UserDto;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecuredAnnotationRule;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

@Controller("/api")
@Secured(SecuredAnnotationRule.IS_AUTHENTICATED)
public class UserInfoController {

    private UserRepository userRepository;

    @Inject
    public UserInfoController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Get("/user/info")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<UserDto> getUserInfo(Principal principal) {

        UserDto userDto = userRepository.findByUsername(principal.getName())
                .flatMap(user -> {
                    UserDto transformedUser = new UserDto();
                    transformedUser.setId(user.getId());
                    transformedUser.setEmail(user.getEmail());
                    transformedUser.setUsername(user.getUsername());
                    transformedUser.setTemporary(user.isTemporary());
                    return Optional.of(transformedUser);
                }).orElseThrow(UserNotFoundException::new);

        return HttpResponse.ok(userDto);
    }
}
