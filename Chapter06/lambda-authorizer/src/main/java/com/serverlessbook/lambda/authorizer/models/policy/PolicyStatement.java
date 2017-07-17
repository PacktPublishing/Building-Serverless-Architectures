package com.serverlessbook.lambda.authorizer.models.policy;

import com.fasterxml.jackson.annotation.JsonGetter;

public class PolicyStatement {

    public enum Effect {

        ALLOW("Allow"),
        DENY("Deny");

        private final String effect;

        Effect(String effect) {
            this.effect = effect;
        }

        public String toString() {
            return effect;
        }
    }

    public final String action;
    public final Effect effect;
    public final String resource;

    public PolicyStatement(String action, Effect effect, String resource) {
        this.action = action;
        this.effect = effect;
        this.resource = resource;
    }

    @JsonGetter("Action")
    public String getAction() {
        return action;
    }

    @JsonGetter("Effect")
    public Effect getEffect() {
        return effect;
    }

    @JsonGetter("Resource")
    public String getResource() {
        return resource;
    }
}
