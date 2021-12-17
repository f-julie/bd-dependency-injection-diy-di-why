package com.amazon.ata.dependencyinjection.discussion.handler;

import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliOperation;
import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliState;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.MemberDao;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.Member;
import com.amazon.ata.input.console.ATAUserHandler;

/**
 * Handles the request to login (or create) a member.
 */
public class LoginHandler implements DiscussionCliOperationHandler {
    private MemberDao memberDao;
    private ATAUserHandler userHandler;

    /**
     * Constructs a new LoginHandler with provided DAO.
     * @param memberDao the DAO for accessing members
     */
    public LoginHandler(MemberDao memberDao, ATAUserHandler userHandler) {
        this.memberDao = memberDao;
        this.userHandler = userHandler;
    }

    @Override
    public String handleRequest(DiscussionCliState state) {
        String username = requestUsername();
        Member member = findOrCreateMember(username);

        if (null == member) {
            state.setNextOperation(DiscussionCliOperation.EXIT);
            return String.format("Failed to log in properly with username '%s'. Exiting.", username);
        }

        state.setCurrentMember(member);
        state.setNextOperation(DiscussionCliOperation.VIEW_TOPICS);

        return renderResponse(member);
    }

    private String requestUsername() {
        return userHandler.getString("", "username: ");
    }

    private Member findOrCreateMember(String username) {
        Member member = memberDao.getMember(username);

        if (null == member) {
            member = new Member(username);
            memberDao.createMember(member);
        }

        return member;
    }

    private String renderResponse(Member member) {
        return String.format("Welcome, %s!", member.display());
    }
}
