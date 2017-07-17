package com.serverlessbook.lambda.userregistration.welcomemail;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.serverlessbook.lambda.SnsLambdaHandler;
import com.serverlessbook.services.user.domain.User;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Objects;

public class Handler extends SnsLambdaHandler<User> {

    private static final Injector INJECTOR = Guice.createInjector();

    private static final Logger LOGGER = Logger.getLogger(Handler.class);

    private AmazonSimpleEmailServiceClient simpleEmailServiceClient;

    @Inject
    public Handler setSimpleEmailServiceClient(
            AmazonSimpleEmailServiceClient simpleEmailServiceClient) {
        this.simpleEmailServiceClient = simpleEmailServiceClient;
        return this;
    }

    public Handler() {
        INJECTOR.injectMembers(this);
        Objects.nonNull(simpleEmailServiceClient);
    }

    private void sendEmail(final User user) {
        final String emailAddress = user.getEmail();
        Destination destination = new Destination().withToAddresses(emailAddress);

        Message message = new Message()
                .withBody(new Body().withText(new Content("Welcome to our forum!")))
                .withSubject(new Content("Welcome!"));

        try {
            LOGGER.debug("Sending welcome mail to " + emailAddress);
            simpleEmailServiceClient.sendEmail(new SendEmailRequest()
                    .withDestination(destination)
                    .withSource(System.getenv("SenderEmail"))
                    .withMessage(message)
            );
            LOGGER.debug("Sending welcome mail to " + emailAddress + " succeeded");
        } catch (Exception anyException) {
            LOGGER.error("Sending welcome mail to " + emailAddress + " failed: ", anyException);
        }

    }

    @Override
    public void handleSnsRequest(User input, Context context) {
        sendEmail(input);
    }

}
