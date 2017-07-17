package com.serverlessbook.services.user;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.serverlessbook.repository.DynamoDBMapperWithCustomTableName;
import com.serverlessbook.services.user.exception.AnotherUserWithSameEmailExistsException;
import com.serverlessbook.services.user.exception.AnotherUserWithSameUsernameExistsException;
import com.serverlessbook.services.user.exception.InvalidMailAddressException;
import com.serverlessbook.services.user.repository.UserRepositoryDynamoDB;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.UUID;


public class UserServiceImplTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private UserService getUserService() {
        return new UserServiceImpl(new UserRepositoryDynamoDB(new DynamoDBMapperWithCustomTableName(new AmazonDynamoDBClient())));
    }

    @Test
    public void failedUserRegistrationWithExistingUsernameTest() throws Exception {

        thrown.expect(AnotherUserWithSameUsernameExistsException.class);

        UserService userService = getUserService();

        final String username = UUID.randomUUID() + "test-username";

        userService.registerNewUser(username, UUID.randomUUID() + "@test.com");
        //Second call should fail
        userService.registerNewUser(username, UUID.randomUUID() + "@test.com");
    }

    @Test
    public void failedUserRegistrationWithExistingEMailTest() throws Exception {

        thrown.expect(AnotherUserWithSameEmailExistsException.class);

        UserService userService = getUserService();

        final String email = UUID.randomUUID() + "@test.com";

        userService.registerNewUser(UUID.randomUUID().toString(), email);
        //Second call should fail
        userService.registerNewUser(UUID.randomUUID().toString(), email);
    }

    @Test
    public void failedUserRegistrationWithInvalidEmailTest() throws Exception {

        thrown.expect(InvalidMailAddressException.class);

        UserService userService = getUserService();

        userService.registerNewUser(UUID.randomUUID().toString(), "INVALID_EMAIL");
    }

}