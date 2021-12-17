package com.amazon.ata.dependencyinjection.discussion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * Provides access to Member items.
 */
public class MemberDao {
    private DynamoDBMapper mapper;

    /**
     * Creates a MemberDao with the given DynamoDBMapper
     * @param mapper The DynamoDBMapper
     */
    public MemberDao(DynamoDBMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Retrieves a Member with the given username, if one exists.
     * @param username The username to look for
     * @return The Member with the given username, if one exists. {@code null} otherwise.
     */
    public Member getMember(String username) {
        if (username.equals("")) {
            throw new IllegalArgumentException("username cannot be empty");
        }

        return mapper.load(Member.class, username);
    }

    /**
     * Saves new member.
     * @param member The Member to create
     * @return The Member that was created
     */
    public Member createMember(Member member) {
        if (null == member.getUsername() || null == member.isActive() || null == member.getKarmaPointsAvailable()) {
            throw new IllegalArgumentException("Member has null field(s): " + member);
        }

        mapper.save(member);
        return member;
    }
}
