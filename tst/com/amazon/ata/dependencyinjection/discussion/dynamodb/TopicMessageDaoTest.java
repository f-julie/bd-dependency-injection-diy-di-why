package com.amazon.ata.dependencyinjection.discussion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Instant;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class TopicMessageDaoTest {
    private TopicMessageDao topicMessageDao;

    @Mock
    private DynamoDBMapper dynamoDBMapper;

    @BeforeEach
    public void setup() {
        initMocks(this);
        topicMessageDao = new TopicMessageDao(dynamoDBMapper);
    }

    @Test
    public void createTopicMessage_withTopicMessageProvided_callsSaveWithTopic() {
        // GIVEN
        // a provided topic message
        TopicMessage topicMessage = new TopicMessage();
        topicMessage.setTopicName("aTopic");
        topicMessage.setAuthor("anAuthor");
        topicMessage.setMessageContent("aMessage");
        topicMessage.setTimestamp(Instant.now().toString());

        // WHEN
        // we call topicMessageDao.createTopicMessage() to save it
        topicMessageDao.createTopicMessage(topicMessage);

        // THEN
        // we should pass the topic message to DynamoDBMapper's save method
        verify(dynamoDBMapper, times(1)).save(topicMessage);
    }
}
