package com.serverlessbook.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameResolver;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;

public class EnvironmentVariableTableNameResolver implements TableNameResolver {

    @Override
    public String getTableName(Class<?> clazz, DynamoDBMapperConfig config) {
        String environmentVariableName = "DynamoDb" + clazz.getSimpleName() + "Table";
        String tableName = System.getenv(environmentVariableName);
        if (tableName == null) {
            throw new DynamoDBMappingException("DynamoDB table name for " + clazz + " cannot be determined. " + environmentVariableName + " environment variable should be set.");
        }
        return tableName;
    }
}
