package com.serverlessbook.lambda.authorizer;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.serverlessbook.lambda.LambdaHandler;
import com.serverlessbook.lambda.authorizer.models.AuthorizationInput;
import com.serverlessbook.lambda.authorizer.models.AuthorizationOutput;
import com.serverlessbook.lambda.authorizer.models.policy.PolicyDocument;
import com.serverlessbook.lambda.authorizer.models.policy.PolicyStatement;
import com.serverlessbook.services.user.UserNotFoundException;
import com.serverlessbook.services.user.UserService;
import com.serverlessbook.services.user.domain.User;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Objects;

public class Handler extends LambdaHandler<AuthorizationInput, AuthorizationOutput> {

    private static final Injector INJECTOR = Guice.createInjector(new DependencyInjectionModule());

    private static final Logger LOGGER = Logger.getLogger(Handler.class);

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Handler() {
        INJECTOR.injectMembers(this);
        Objects.requireNonNull(userService);
    }

    @Override
    public AuthorizationOutput handleRequest(AuthorizationInput input, Context context) {
        final String authenticationToken = input.getAuthorizationToken();
        final PolicyDocument policyDocument = new PolicyDocument();
        PolicyStatement.Effect policyEffect = PolicyStatement.Effect.ALLOW;
        String principalId = null;

        try {
            User authenticatedUser = userService.getUserByToken(authenticationToken);
            principalId = String.valueOf(authenticatedUser.getId());
        } catch (UserNotFoundException userNotFoundException) {
            policyEffect = PolicyStatement.Effect.DENY;
            LOGGER.info("User authentication failed for token " + authenticationToken);
        }

        policyDocument.withPolicyStatement(new PolicyStatement("execute-api:Invoke",
                policyEffect, input.getMethodArn()));
        return new AuthorizationOutput(principalId, policyDocument);
    }
}
