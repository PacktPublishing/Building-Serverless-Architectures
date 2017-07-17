package com.serverlessbook.lambda.test;

import com.amazonaws.services.lambda.runtime.Context;
import com.serverlessbook.lambda.LambdaHandler;
import org.apache.log4j.Logger;

public class Handler extends LambdaHandler<Handler.TestInput, Handler.TestOutput> {

    static final Logger LOGGER = Logger.getLogger(Handler.class);

    static class TestInput {
        public String value;
    }

    static class TestOutput {
        public String value;
    }

    @Override
    public TestOutput handleRequest(TestInput input, Context context) {

        LOGGER.debug("Input from Lambda event " + input.value);

        TestOutput testOutput = new TestOutput();
        testOutput.value = input.value;
        return testOutput;
    }
}
