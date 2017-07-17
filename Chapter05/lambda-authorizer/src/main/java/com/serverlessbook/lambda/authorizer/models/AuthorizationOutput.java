package com.serverlessbook.lambda.authorizer.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.serverlessbook.lambda.authorizer.models.policy.PolicyDocument;

public class AuthorizationOutput {

    private final String principalId;

    private final PolicyDocument policyDocument;

    public AuthorizationOutput(String principalId, PolicyDocument policyDocument) {
        this.principalId = principalId;
        this.policyDocument = policyDocument;
    }

    @JsonGetter("principalId")
    public String getPrincipalId() {
        return principalId;
    }

    @JsonGetter("policyDocument")
    public PolicyDocument getPolicyDocument() {
        return policyDocument;
    }
}
