package com.serverlessbook.services.user.repository;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.serverlessbook.services.user.domain.Token;
import com.serverlessbook.services.user.domain.User;

import javax.inject.Inject;
import java.util.Optional;

public class UserRepositoryDynamoDB implements UserRepository {

    private final DynamoDBMapper dynamoDBMapper;

    @Inject
    public UserRepositoryDynamoDB(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public Optional<User> getUserByToken(String token) {
        Token foundTokenInDynamoDB = dynamoDBMapper.load(Token.class, token);
        if (foundTokenInDynamoDB != null) {
            // Token found in DynamoDb, try to fetch the user in a second query
            return Optional.ofNullable(dynamoDBMapper.load(User.class, foundTokenInDynamoDB.getUserId()));
        }
        // Token not found, return empty.
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {
        dynamoDBMapper.save(user);
    }

    public Optional<User> getUserByCriteria(String indexName, User hashKeyValues) {

        DynamoDBQueryExpression<User> expression = new DynamoDBQueryExpression<User>()
                .withIndexName(indexName)
                .withConsistentRead(false)
                .withHashKeyValues(hashKeyValues);

        QueryResultPage<User> result = dynamoDBMapper.queryPage(User.class, expression);

        if (result.getCount() > 0) {
            return Optional.of(result.getResults().get(0));
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return getUserByCriteria("EmailIndex", new User().setEmail(email));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return getUserByCriteria("UsernameIndex", new User().setUsername(username));
    }
}
