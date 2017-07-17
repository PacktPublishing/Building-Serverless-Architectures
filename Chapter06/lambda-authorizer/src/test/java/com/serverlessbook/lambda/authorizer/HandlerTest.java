package com.serverlessbook.lambda.authorizer;

import com.serverlessbook.lambda.authorizer.models.AuthorizationInput;
import com.serverlessbook.lambda.authorizer.models.AuthorizationOutput;
import com.serverlessbook.lambda.authorizer.models.policy.PolicyStatement;
import org.junit.Test;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

public class HandlerTest {

    @Test
    public void testDependencies() throws Exception {
        Handler testHandler = new Handler();
    }

    @Test
    public void testFailingToken() throws Exception {
        Handler testHandler = new Handler();
        AuthorizationInput mockEvent = createNiceMock(AuthorizationInput.class);
        expect(mockEvent.getAuthorizationToken()).andReturn("INVALID_TOKEN").anyTimes();
        replay(mockEvent);

        AuthorizationOutput authorizationOutput = testHandler.handleRequest(mockEvent, null);
        assertEquals(PolicyStatement.Effect.DENY, authorizationOutput.getPolicyDocument().getPolicyStatements().get(0).getEffect());
    }
}