package com.serverlessbook.lambda.userregistration.search;


import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClient;
import com.amazonaws.services.cloudsearchdomain.model.ContentType;
import com.amazonaws.services.cloudsearchdomain.model.UploadDocumentsRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.serverlessbook.lambda.SnsLambdaHandler;
import com.serverlessbook.services.user.domain.User;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Handler extends SnsLambdaHandler<User> {

    private static final Injector INJECTOR = Guice.createInjector();

    private static final Logger LOGGER = Logger.getLogger(Handler.class);

    private AmazonCloudSearchDomainClient amazonCloudSearchDomainClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Inject
    public Handler setAmazonCloudSearchDomainClient(AmazonCloudSearchDomainClient amazonCloudSearchDomainClient) {
        this.amazonCloudSearchDomainClient = amazonCloudSearchDomainClient;
        this.amazonCloudSearchDomainClient.setEndpoint(System.getenv("CloudSearchDomain"));
        return this;
    }

    public Handler() {
        INJECTOR.injectMembers(this);
        Objects.nonNull(amazonCloudSearchDomainClient);
    }

    private void uploadDocument(User user) {
        try {
            final Map<String, Object> documentRequest = new HashMap<>();
            documentRequest.put("type", "add");
            documentRequest.put("id", user.getId());
            documentRequest.put("fields", user);
            LOGGER.info("User with id " + user.getId() + " is being uploaded to CloudSearch");
            byte[] jsonAsByteStream = objectMapper.writeValueAsBytes(new Map[]{documentRequest});
            if (jsonAsByteStream != null) {
                ByteArrayInputStream document = new ByteArrayInputStream(jsonAsByteStream);
                amazonCloudSearchDomainClient.uploadDocuments(new UploadDocumentsRequest()
                        .withDocuments(document)
                        .withContentLength((long) document.available())
                        .withContentType(ContentType.Applicationjson)
                );
            }
        } catch (JsonProcessingException jsonProcessingException) {
            LOGGER.error("Object could not be converted to JSON", jsonProcessingException);
        } catch (Exception anyException) {
            LOGGER.error("Upload was failing", anyException);
        }
    }

    @Override
    public void handleSnsRequest(User input, Context context) {
        uploadDocument(input);
    }

}
