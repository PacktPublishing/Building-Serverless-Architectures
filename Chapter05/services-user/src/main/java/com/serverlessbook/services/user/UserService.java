package com.serverlessbook.services.user;


import com.serverlessbook.services.user.domain.User;
import com.serverlessbook.services.user.exception.UserRegistrationException;

public interface UserService {
    User getUserByToken(String token) throws UserNotFoundException;

    User registerNewUser(String username, String email) throws UserRegistrationException;
}
