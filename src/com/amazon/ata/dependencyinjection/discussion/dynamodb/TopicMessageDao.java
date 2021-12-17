package com.amazon.ata.dependencyinjection.discussion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides access to TopicMessages.
 */
public class TopicMessageDao {
    private DynamoDBMapper mapper;

    /**
     * Constructs a TopicMessageDao with the provided DynamoDBMapper
     * @param mapper
     */
    public TopicMessageDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves topic messages for the specified topic name.
     * @param topicName The topic name to look for topic messages on
     * @return A list of TopicMessages associated with the given topicName
     */
    public List<TopicMessage> getMessagesForTopicName(String topicName) {
        Map<String, AttributeValue> attributeValues = ImmutableMap.of(":t", new AttributeValue().withS(topicName));
        DynamoDBQueryExpression<TopicMessage> query =
            new DynamoDBQueryExpression<TopicMessage>()
                .withKeyConditionExpression("topicName = :t")
                .withExpressionAttributeValues(attributeValues);

        return new ArrayList<>(mapper.query(TopicMessage.class, query));
    }

    /**
     * Creates a new topic from the TopicMessage object provided.
     * @param topicMessage the new TopicMessage
     * @return The topicMessage that was created
     */
    public TopicMessage createTopicMessage(TopicMessage topicMessage) {
        mapper.save(topicMessage);
        return topicMessage;
    }
}
