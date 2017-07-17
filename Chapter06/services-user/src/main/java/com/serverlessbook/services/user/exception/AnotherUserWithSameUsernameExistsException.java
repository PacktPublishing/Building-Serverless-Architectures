package com.serverlessbook.services.user.exception;


public class AnotherUserWithSameUsernameExistsException extends UserRegistrationException {

    private static final long serialVersionUID = 4824390458386666422L;

    public AnotherUserWithSameUsernameExistsException() {
        super("Another user with same username already exists.");
    }
}
