package com.serverlessbook.lambda.imageresizer;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.apache.log4j.Logger;

public class Handler implements RequestHandler<S3Event, Void> {

  private static final Logger LOGGER = Logger.getLogger(Handler.class);

  final AmazonS3 s3client;

  public Handler() {
    s3client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
  }

  private void resizeImage(String bucket, String key) {
    LOGGER.info("Resizing s3://" + bucket + "/" + key);
    final String userId = s3client.getObjectMetadata(bucket, key).getUserMetaDataOf("user-id");
    LOGGER.info("Image is belonging to " + userId);
    final String destinationKey = "users/" + userId + "/picture/small.jpg";
    s3client.copyObject(bucket, key,
        bucket, destinationKey);
    LOGGER.info("Image has been copied to s3://" + bucket + "/" + destinationKey);
  }

  @Override
  public Void handleRequest(S3Event input, Context context) {
    input.getRecords().forEach(s3EventNotificationRecord ->
        resizeImage(s3EventNotificationRecord.getS3().getBucket().getName(),
            s3EventNotificationRecord.getS3().getObject().getKey()));
    return null;
  }
}
