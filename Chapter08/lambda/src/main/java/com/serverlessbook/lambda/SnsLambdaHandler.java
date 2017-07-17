package com.serverlessbook.lambda;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public abstract class SnsLambdaHandler<I> implements RequestHandler<SNSEvent, Void> {

    private static final Logger LOGGER = Logger.getLogger(SnsLambdaHandler.class);

    private final ObjectMapper objectMapper;

    protected SnsLambdaHandler() {
        objectMapper=new ObjectMapper();
    }

    public abstract void handleSnsRequest(I input, Context context);


    @SuppressWarnings("unchecked")
    private Class<I> getJsonType() {
        return (Class<I>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public Void handleRequest(SNSEvent input, Context context) {
        input.getRecords().forEach(snsMessage -> {
            try {
                I deserializedPayload = objectMapper.readValue(snsMessage.getSNS().getMessage(), getJsonType());
                handleSnsRequest(deserializedPayload, context);
            } catch (IOException anyException) {
                LOGGER.error("JSON could not be deserialized", anyException);
            }
        });
        return null;
    }
}
