package com.valychbreak.calculator.service.user;

import com.valychbreak.calculator.domain.User;
import com.valychbreak.calculator.exception.UserNotFoundException;
import com.valychbreak.calculator.repository.UserRepository;
import com.valychbreak.calculator.service.AsyncRepositoryCallExecutor;
import reactor.core.publisher.Mono;

import javax.inject.Singleton;
import java.security.Principal;

@Singleton
public class UserService {
    private final UserRepository userRepository;
    private final AsyncRepositoryCallExecutor asyncRepositoryCallExecutor;

    public UserService(UserRepository userRepository, AsyncRepositoryCallExecutor asyncRepositoryCallExecutor) {
        this.userRepository = userRepository;
        this.asyncRepositoryCallExecutor = asyncRepositoryCallExecutor;
    }

    public Mono<User> findUserBy(Principal principal) {
        return findUserBy(principal.getName());
    }

    public Mono<User> findUserBy(String username) {
        return asyncRepositoryCallExecutor.async(() -> userRepository.findByUsername(username))
                .flatMap(Mono::justOrEmpty)
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }

    public Mono<User> findUserBy(String username, String password) {
        return asyncRepositoryCallExecutor.async(() -> userRepository.findByUsername(username))
                .flatMap(Mono::justOrEmpty)
                .filter(user -> password.equals(user.getPassword()))
                .switchIfEmpty(Mono.error(UserNotFoundException::new));
    }

    public Mono<Void> delete(User user) {
        return Mono.fromRunnable(() -> userRepository.delete(user));
    }
}
