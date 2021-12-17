package com.amazon.ata.dependencyinjection.discussion.handler;

import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliOperation;
import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliState;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.TopicMessage;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.TopicMessageDao;
import com.amazon.ata.input.console.ATAUserHandler;

import java.time.Instant;

/**
 * Handler for the CREATE_TOPIC operation
 */
public class CreateTopicMessageHandler implements DiscussionCliOperationHandler {
    private ATAUserHandler userHandler;
    private TopicMessageDao topicMessageDao;

    /**
     * Constructs handler with its dependencies.
     * @param userHandler the ATAUserHandler, for user input
     */
    public CreateTopicMessageHandler(ATAUserHandler userHandler, TopicMessageDao topicMessageDao) {
        this.userHandler = userHandler;
        this.topicMessageDao = topicMessageDao;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        if (null == state.getCurrentMember()) {
            throw new IllegalStateException(
                "Encountered request to create topic message but there is no current member. Exiting"
            );
        }
        if (null == state.getCurrentTopic()) {
            state.setNextOperation(DiscussionCliOperation.VIEW_TOPICS);
            return "You must select a topic first.";
        }

        String messageContent = userHandler.getString("Message:");
        TopicMessage topicMessage = new TopicMessage();
        // set topic name to the current topic in the DiscussionCliState object
        topicMessage.setTopicName(state.getCurrentTopic().getName());
        // set author to the current logged in member in the DiscussionCliState object
        topicMessage.setAuthor(state.getCurrentMember().getUsername());
        // set the topic message timestamp to when it was created
        topicMessage.setTimestamp(Instant.now().toString());
        topicMessage.setMessageContent(messageContent);

        topicMessageDao.createTopicMessage(topicMessage);

        state.setNextOperation(DiscussionCliOperation.VIEW_TOPIC_MESSAGES);

        return String.format("New message '%s' by [%s] was created!", topicMessage.getMessageContent(),
            topicMessage.getAuthor());
    }
}
