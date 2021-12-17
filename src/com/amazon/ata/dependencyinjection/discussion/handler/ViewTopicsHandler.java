package com.amazon.ata.dependencyinjection.discussion.handler;


import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliState;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.Topic;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.TopicDao;
import com.amazon.ata.input.console.ATAUserHandler;
import com.amazon.ata.string.TextTable;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Handler for the VIEW_TOPICS operation.
 */
public class ViewTopicsHandler implements DiscussionCliOperationHandler {
    private static final int MAX_NUM_TOPICS_TO_VIEW = 100;

    private final TopicDao topicDao;
    private final ATAUserHandler userHandler;

    /**
     * Constructs handler with its dependencies.
     * @param topicDao The TopicDao
     * @param userHandler the ATAUserHandler, for user input
     */
    public ViewTopicsHandler(TopicDao topicDao, ATAUserHandler userHandler) {
        this.topicDao = topicDao;
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        int numTopics = userHandler.getInteger(
            1,
            MAX_NUM_TOPICS_TO_VIEW,
            "We'll display topics. Select maximum number of topics to view in case there are many: "
        );
        List<Topic> topics = topicDao.getTopics(numTopics);
        state.setListedTopics(topics);
        return renderTopics(topics);
    }

    private String renderTopics(List<Topic> topics) {
        List<String> headers = ImmutableList.of("#", "Topic name");
        List<List<String>> rows = new ArrayList<>();
        int topicNum = 0;
        for (Topic topic : topics) {
            List<String> row = new ArrayList<>();
            row.add(Integer.toString(topicNum++));
            row.add(topic.getName());
            rows.add(row);
        }

        return new TextTable(headers, rows).toString();
    }
}
