package com.serverlessbook.services.user.repository;

import com.serverlessbook.services.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> getUserByToken(String token);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserByUsername(String username);

    void saveUser(User user);
}