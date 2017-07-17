package com.serverlessbook.lambda.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.serverlessbook.lambda.LambdaHandler;

public class Handler extends LambdaHandler<Handler.TestInput, Handler.TestOutput> {

    static class TestInput {
        public String value;
    }

    static class TestOutput {
        public String value;
    }

    @Override
    public TestOutput handleRequest(TestInput input, Context context) {
        TestOutput testOutput = new TestOutput();
        testOutput.value = input.value;
        return testOutput;
    }
}
