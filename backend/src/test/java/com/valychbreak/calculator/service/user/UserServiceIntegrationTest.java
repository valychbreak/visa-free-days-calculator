package com.valychbreak.calculator.service.user;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.repository.UserRepository;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.test.StepVerifier;

import javax.inject.Inject;
import javax.inject.Named;

import java.security.Principal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("SameParameterValue")
@MicronautTest
class UserServiceIntegrationTest {

    private static final String USERNAME = "testUserService";

    @Inject
    private UserService userService;

    @Inject
    private UserRepository userRepository;

    @Inject
    @Named("jdbcScheduler")
    private Scheduler scheduler;

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser(USERNAME);
    }

    @Test
    void shouldFindUserByUsername() {
        // given
        var userMono = getCreateUserMono(user);

        // when
        var composite = Mono.from(userMono)
                .then(userService.findUserBy(USERNAME));

        // then
        StepVerifier.create(composite)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldFindUserByPrincipal() {
        // given
        var userMono = getCreateUserMono(user);

        // when
        var composite = Mono.from(userMono)
                .then(userService.findUserBy(createPrincipal(USERNAME)));

        // then
        StepVerifier.create(composite)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldDeleteUser() {
        // given
        var createUser = getCreateUserMono(user);

        // when
        var delete = Mono.from(userService.delete(user));

        // then
        var find = Mono.fromCallable(() -> userRepository.findByUsername(USERNAME))
                .flatMap(Mono::justOrEmpty);

        var composite = Mono.from(createUser)
                .then(delete)
                .then(find);

        StepVerifier.create(composite)
                .expectComplete()
                .verify();
    }

    private Mono<User> getCreateUserMono(User user) {
        return Mono.fromRunnable(() -> userRepository.deleteAll())
                .then(Mono.fromCallable(() -> userRepository.save(user)))
                .subscribeOn(scheduler);
    }

    private Principal createPrincipal(String name) {
        var principal = mock(Principal.class);
        when(principal.getName()).thenReturn(name);
        return principal;
    }

    private User createUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("test");
        user.setEmail(username + "@t.com");
        return user;
    }
}