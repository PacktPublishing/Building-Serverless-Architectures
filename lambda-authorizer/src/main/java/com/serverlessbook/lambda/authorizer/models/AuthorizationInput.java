package com.serverlessbook.lambda.authorizer.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorizationInput {

    @JsonProperty("authorizationToken")
    private String authorizationToken;

    @JsonProperty("methodArn")
    private String methodArn;

    @JsonProperty("type")
    private String type;

    /**
     * Returns the Authorization token given in the request
     *
     * @return Authorization token
     */
    public String getAuthorizationToken() {
        return authorizationToken.split(" ", 2)[1];
    }

    /**
     * Returns the invoked API Gateway Method's ARN.
     *
     * @return Method ARN
     */
    public String getMethodArn() {
        return methodArn;
    }

    /**
     * Payload type. Currently the only value is TOKEN
     *
     * @return Payload type
     */
    public String getType() {
        return type;
    }
}