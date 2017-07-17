package com.serverlessbook.services.user;

import com.serverlessbook.services.user.domain.User;
import com.serverlessbook.services.user.exception.AnotherUserWithSameEmailExistsException;
import com.serverlessbook.services.user.exception.AnotherUserWithSameUsernameExistsException;
import com.serverlessbook.services.user.exception.InvalidMailAddressException;
import com.serverlessbook.services.user.exception.UserRegistrationException;
import com.serverlessbook.services.user.repository.UserRepository;

import javax.inject.Inject;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        Objects.requireNonNull(userRepository);
    }

    @Override
    public User getUserByToken(String token) throws UserNotFoundException {
        return userRepository.getUserByToken(token).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User registerNewUser(String username, String email) throws UserRegistrationException {

        checkEmailValidity(email);
        checkEmailUniqueness(email);
        checkUsernameUniqueness(username);

        User newUser = new User()
                .setId(UUID.randomUUID().toString())
                .setUsername(username)
                .setEmail(email);

        userRepository.saveUser(newUser);
        return newUser;
    }

    private void checkEmailValidity(String email) throws InvalidMailAddressException {
        final String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        if (!Pattern.compile(emailPattern).matcher(email).matches()) {
            throw new InvalidMailAddressException();
        }
    }

    void checkEmailUniqueness(String email) throws AnotherUserWithSameEmailExistsException {
        if (userRepository.getUserByEmail(email).isPresent()) {
            throw new AnotherUserWithSameEmailExistsException();
        }
    }

    void checkUsernameUniqueness(String username) throws AnotherUserWithSameUsernameExistsException {
        if (userRepository.getUserByUsername(username).isPresent()) {
            throw new AnotherUserWithSameUsernameExistsException();
        }
    }

}
