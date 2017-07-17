package com.serverlessbook.lambda.authorizer.models.policy;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolicyDocument {

    private final List<PolicyStatement> policyStatements = new ArrayList<>();

    @JsonIgnore
    public PolicyDocument withPolicyStatement(PolicyStatement policyStatement) {
        policyStatements.add(policyStatement);
        return this;
    }

    @JsonGetter("Version")
    public String getVersion() {
        return "2012-10-17";
    }

    @JsonGetter("Statement")
    public List<PolicyStatement> getPolicyStatements() {
        return Collections.unmodifiableList(policyStatements);
    }
}
