package com.serverlessbook.services.user.exception;


public class InvalidMailAddressException extends UserRegistrationException {

    private static final long serialVersionUID = 4033382620357808779L;

    public InvalidMailAddressException() {
        super("This E-Mail address is not valid");
    }
}
