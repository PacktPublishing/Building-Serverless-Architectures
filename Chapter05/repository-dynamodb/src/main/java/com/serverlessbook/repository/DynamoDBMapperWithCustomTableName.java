package com.serverlessbook.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

import javax.inject.Inject;

public class DynamoDBMapperWithCustomTableName extends DynamoDBMapper {

    @Inject
    public DynamoDBMapperWithCustomTableName(AmazonDynamoDBClient amazonDynamoDBClient) {
        this(amazonDynamoDBClient, new EnvironmentVariableTableNameResolver());
    }

    public DynamoDBMapperWithCustomTableName(AmazonDynamoDBClient amazonDynamoDBClient, DynamoDBMapperConfig.TableNameResolver tableNameResolver) {
        super(amazonDynamoDBClient,
                DynamoDBMapperConfig
                        .builder()
                        .withTableNameResolver(tableNameResolver)
                        .build());
    }
}
