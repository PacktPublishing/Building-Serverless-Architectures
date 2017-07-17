package com.serverlessbook.services.user.domain;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;

public class User {

    @DynamoDBHashKey(attributeName = "UserId")
    private String id;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "UsernameIndex", attributeName = "Username")
    private String username;

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "EmailIndex", attributeName = "Email")
    private String email;

    public String getId() {
        return id;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }
}