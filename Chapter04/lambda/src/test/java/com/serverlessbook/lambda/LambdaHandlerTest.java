package com.serverlessbook.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class LambdaHandlerTest {

    protected static class TestInput {
        public String value;
    }

    protected static class TestOutput {
        public String value;
    }

    protected static class TestLambdaHandler extends LambdaHandler<TestInput, TestOutput> {
        @Override
        public TestOutput handleRequest(TestInput input, Context context) {
            TestOutput testOutput = new TestOutput();
            testOutput.value = input.value;
            return testOutput;
        }
    }

    @Test
    public void handleRequest() throws Exception {
        String jsonInputAndExpectedOutput = "{\"value\":\"testValue\"}";
        InputStream exampleInputStream = new ByteArrayInputStream(jsonInputAndExpectedOutput.getBytes(StandardCharsets.UTF_8));
        OutputStream exampleOutputStream = new OutputStream() {

            private final StringBuilder stringBuilder = new StringBuilder();

            @Override
            public void write(int b) {
                stringBuilder.append((char) b);
            }

            @Override
            public String toString() {
                return stringBuilder.toString();
            }
        };

        TestLambdaHandler lambdaHandler = new TestLambdaHandler();
        lambdaHandler.handleRequest(exampleInputStream, exampleOutputStream, null);
        assertEquals(jsonInputAndExpectedOutput, exampleOutputStream.toString());
    }
}
