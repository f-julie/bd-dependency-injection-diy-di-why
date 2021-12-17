package com.amazon.ata.dependencyinjection.discussion.handler;

import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliOperation;
import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliState;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.Topic;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.TopicDao;
import com.amazon.ata.input.console.ATAUserHandler;

/**
 * Handler for the CREATE_TOPIC operation
 */
public class CreateTopicHandler implements DiscussionCliOperationHandler {
    private final TopicDao topicDao;
    private final ATAUserHandler userHandler;

    /**
     * Constructs handler with its dependencies.
     * @param topicDao TopicDao
     * @param userHandler the ATAUserHandler, for user input
     */
    public CreateTopicHandler(TopicDao topicDao, ATAUserHandler userHandler) {
        this.topicDao = topicDao;
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        String topicName = userHandler.getString("", "New topic name: ");
        String description = userHandler.getString("", "New topic description: ");

        Topic newTopic = new Topic(topicName, description);
        newTopic = topicDao.createTopic(newTopic);
        state.setCurrentTopic(newTopic);
        state.setNextOperation(DiscussionCliOperation.VIEW_TOPIC_MESSAGES);

        return String.format("New topic '%s' was created!", newTopic.getName());
    }
}
