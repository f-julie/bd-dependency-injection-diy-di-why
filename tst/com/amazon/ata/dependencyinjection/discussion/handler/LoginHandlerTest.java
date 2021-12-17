package com.amazon.ata.dependencyinjection.discussion.handler;

import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliOperation;
import com.amazon.ata.dependencyinjection.discussion.cli.DiscussionCliState;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.Member;
import com.amazon.ata.dependencyinjection.discussion.dynamodb.MemberDao;
import com.amazon.ata.input.console.ATAUserHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LoginHandlerTest {
    private LoginHandler handler;
    private DiscussionCliState state;

    @Mock
    private MemberDao memberDao;
    @Mock
    private ATAUserHandler userHandler;

    @BeforeEach
    private void setup() {
        initMocks(this);
        handler = new LoginHandler(memberDao, userHandler);
        state = getCliState();
    }

    @Test
    void handleRequest_withNonexistentUsername_createsNewMember() {
        // GIVEN
        // new username
        String newUsername = "new username";
        // new member that would be returned
        Member newMember = new Member(newUsername);
        // User input is the new username
        when(userHandler.getString(anyString(), anyString())).thenReturn(newUsername);
        // DAO doesn't find username and allows creation
        when(memberDao.getMember(newUsername)).thenReturn(null);

        // WHEN
        String result = handler.handleRequest(state);

        // THEN
        // member is created
        verify(memberDao).createMember(newMember);
        assertTrue(result.contains(newUsername));
    }

    @Test
    void handleRequest_withExistingUsername_returnsExistingMember() {
        // GIVEN
        // existing member's username
        String existingName = "oldDiscuzzer";
        // existing member that would be returned
        Member existingMember = new Member(existingName);
        existingMember.setKarmaPointsAvailable(1001);
        // User input is the existing username
        when(userHandler.getString(anyString(), anyString())).thenReturn(existingName);
        // DAO finds member
        when(memberDao.getMember(existingName)).thenReturn(existingMember);

        // WHEN
        String result = handler.handleRequest(state);

        // THEN
        verify(memberDao, times(0)).createMember(any());
        assertTrue(result.contains(existingName));
    }

    @Test
    void handleRequest_whenSuccessWithNewUsername_nextOperationIsViewTopics() {
        // GIVEN
        // User input is the new username
        when(userHandler.getString(anyString(), anyString())).thenReturn("");
        // DAO doesn't find username and allows creation
        when(memberDao.getMember(anyString())).thenReturn(null);
        when(memberDao.createMember(any())).thenReturn(new Member());

        // WHEN
        handler.handleRequest(state);

        // THEN
        // state says next operation is view topics
        assertEquals(DiscussionCliOperation.VIEW_TOPICS, state.getNextOperation());
    }

    @Test
    void handleRequest_whenSuccessWithExistingUsername_nextOperationIsViewTopics() {
        // GIVEN
        // User input is the new username
        when(userHandler.getString(anyString(), anyString())).thenReturn("");
        // DAO doesn't find username and allows creation
        when(memberDao.getMember(anyString())).thenReturn(new Member());

        // WHEN
        handler.handleRequest(state);

        // THEN
        // state says next operation is view topics
        assertEquals(DiscussionCliOperation.VIEW_TOPICS, state.getNextOperation());
    }

    private DiscussionCliState getCliState() {
        return new DiscussionCliState();
    }
}
