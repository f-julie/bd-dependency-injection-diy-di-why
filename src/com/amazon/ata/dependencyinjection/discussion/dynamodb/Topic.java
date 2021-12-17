package com.amazon.ata.dependencyinjection.discussion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

/**
 * A topic in the Discussion app, which may have many TopicMessages associated
 * with it.
 */
@DynamoDBTable(tableName = "DependencyInjection-Topics")
public class Topic {
    private static final int MAX_NAME_LENGTH = 50;

    private String name;
    private String description;
    private Boolean isArchived;

    /**
     * Constructs a name/description-less Topic.
     */
    public Topic() {
        this(null, null);
    }

    /**
     * Constructs a Topic with the given name and description.
     * @param name the topic name
     * @param description the topic description
     */
    public Topic(String name, String description) {
        this.setName(name);
        this.description = description;
        this.isArchived = false;
    }

    @DynamoDBHashKey(attributeName = "name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name, validating topic name isn't longer than MAX_NAME_LENGTH.
     * @param name Topic's new name
     */
    public void setName(String name) {
        if (null != name && name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Topic name cannot be longer than " + MAX_NAME_LENGTH);
        }
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "isArchived")
    public Boolean isArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Topic topic = (Topic) o;
        return Objects.equals(name, topic.name) &&
               Objects.equals(description, topic.description) &&
               Objects.equals(isArchived, topic.isArchived);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, isArchived);
    }
}
