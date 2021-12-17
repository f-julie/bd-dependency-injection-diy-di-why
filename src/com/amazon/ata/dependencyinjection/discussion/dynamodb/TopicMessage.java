package com.amazon.ata.dependencyinjection.discussion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A message on a particular topic in the Discussion app.
 */
@DynamoDBTable(tableName = "DependencyInjection-TopicMessages")
public class TopicMessage {
    private static final String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);

    private String topicName;
    private String timestamp;
    private String author;
    private String messageContent;

    /**
     * Constructs a new TopicMessage, assigning a timestamp to now.
     */
    public TopicMessage() {
        timestamp = ZonedDateTime.now(ZoneId.of("UTC")).format(TIMESTAMP_FORMATTER);
    }

    @DynamoDBHashKey(attributeName = "topicName")
    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @DynamoDBRangeKey(attributeName = "timestamp")
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDBAttribute(attributeName = "author")
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @DynamoDBAttribute(attributeName = "messageContent")
    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopicMessage that = (TopicMessage) o;
        return Objects.equals(topicName, that.topicName) &&
               Objects.equals(timestamp, that.timestamp) &&
               Objects.equals(author, that.author) &&
               Objects.equals(messageContent, that.messageContent);
    }

    @Override
    public int hashCode() {

        return Objects.hash(topicName, timestamp, author, messageContent);
    }
}
