package com.amazon.ata.dependencyinjection.discussion.handler;

import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliOperation;
import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliState;

/**
 * Handler for the EXIT operation.
 */
public class ExitHandler implements DiscussionCliOperationHandler {
    @Override
    public String handleRequest(DiscussionCliState state) {
        state.setNextOperation(DiscussionCliOperation.EXIT);
        return "Hope you enjoyed, good-bye!";
    }
}
