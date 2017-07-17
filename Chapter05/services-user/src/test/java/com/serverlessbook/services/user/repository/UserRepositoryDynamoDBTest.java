package com.serverlessbook.services.user.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.serverlessbook.repository.DynamoDBMapperWithCustomTableName;
import com.serverlessbook.services.user.domain.User;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;


public class UserRepositoryDynamoDBTest {

    private UserRepository getUserRepository() {
        return new UserRepositoryDynamoDB(new DynamoDBMapperWithCustomTableName(new AmazonDynamoDBClient()));
    }

    @Test
    public void saveAndRetrieveUser() throws Exception {
        final String email = "test@test.com";
        final String password = "test-password";
        final String username = "test-username";
        final String id = UUID.randomUUID().toString();

        User newUser = new User()
                .setEmail(email)
                .setUsername(username)
                .setId(id);

        getUserRepository().saveUser(newUser);
        assertEquals(email, getUserRepository().getUserByEmail(email).orElseThrow(RuntimeException::new).getEmail());
        assertEquals(username, getUserRepository().getUserByUsername(username).orElseThrow(RuntimeException::new).getUsername());
    }

}