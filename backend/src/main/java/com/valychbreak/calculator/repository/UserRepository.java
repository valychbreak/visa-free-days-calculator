package com.valychbreak.calculator.repository;

import com.valychbreak.calculator.domain.User;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Singleton
public class UserRepository {

    private static AtomicLong nextId = new AtomicLong(1L);

    private List<User> users = new ArrayList<>();

    public User save(User user) {
        user.setId(nextId.getAndIncrement());
        users.add(user);
        return user;
    }

    public Collection<User> findAll() {
        return users;
    }
}
