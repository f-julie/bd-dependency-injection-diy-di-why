package com.amazon.ata.dependencyinjection.discussion.handler;

import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliState;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.Topic;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.TopicDao;
import com.amazon.ata.input.console.ATAUserHandler;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ViewTopicsHandlerTest {
    private ViewTopicsHandler handler;
    private DiscussionCliState state;

    @Mock
    private TopicDao topicDao;
    @Mock
    private ATAUserHandler userHandler;

    @BeforeEach
    private void setup() {
        initMocks(this);
        handler = new ViewTopicsHandler(topicDao, userHandler);
        state = getCliState();
    }

    @Test
    void handleRequest_withNoTopics_displaysEmptyTable() {
        // GIVEN
        // number of topics requested by user
        int numTopics = 1;
        when(userHandler.getInteger(anyInt(), anyInt())).thenReturn(numTopics);
        // DAO returns empty list of topics
        when(topicDao.getTopics(numTopics)).thenReturn(Collections.emptyList());

        // WHEN
        String result = handler.handleRequest(state);

        // THEN
        assertTrue(result.contains("Topic name"));
    }

    @Test
    void handleRequest_withTopic_displaysTopicName() {
        // GIVEN
        // Topic in db
        Topic existingTopic = new Topic("a topic name", "my description");
        // number of topics requested by user
        int numTopics = 2;

        when(userHandler.getInteger(anyInt(), anyInt(), anyString())).thenReturn(numTopics);
        // DAO returns topic
        when(topicDao.getTopics(numTopics)).thenReturn(ImmutableList.of(existingTopic));

        // WHEN
        String result = handler.handleRequest(state);

        // THEN
        assertTrue(result.contains(existingTopic.getName()));
    }

    @Test
    void handleRequest_afterSuccessful_doesNotSetNextOperation() {
        // GIVEN
        // Topic in db
        Topic existingTopic = new Topic("a topic name", "my description");
        // number of topics requested by user
        int numTopics = 2;
        when(userHandler.getInteger(anyInt(), anyInt())).thenReturn(numTopics);
        // DAO returns topic
        when(topicDao.getTopics(numTopics)).thenReturn(ImmutableList.of(existingTopic));

        // WHEN
        handler.handleRequest(state);

        // THEN
        assertNull(state.getNextOperation());
    }

    private DiscussionCliState getCliState() {
        return new DiscussionCliState();
    }
}
