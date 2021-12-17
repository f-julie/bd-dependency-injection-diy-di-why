package com.amazon.ata.dependencyinjection.discussion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.MockitoAnnotations.initMocks;

public class TopicDaoTest {
    private TopicDao topicDao;

    @Mock
    private DynamoDBMapper mapper;

    @BeforeEach
    private void setup() {
        initMocks(this);
        topicDao = new TopicDao(mapper);
    }

    @Test
    void createTopic_withValidTopic_isSavedAndReturned() {
        // GIVEN
        // topic to be saved
        Topic newTopic = new Topic("my topic name", "my topic description");
        // DynamoDB accepts it
        doNothing().when(mapper).save(newTopic);

        // WHEN
        Topic result = topicDao.createTopic(newTopic);

        // THEN
        assertEquals(newTopic, result);
    }
}
