package com.amazon.ata.dependencyinjection.discussion.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class MemberDaoTest {
    private MemberDao memberDao;

    @Mock
    private DynamoDBMapper mapper;

    @BeforeEach
    private void setup() {
        initMocks(this);
        memberDao = new MemberDao(mapper);
    }

    @Test
    void getMember_whenDynamoDbDoesntHaveMemberWithUsername_returnsNull() {
        // GIVEN
        // nonexistent username
        String nonexistentUsername = "no such username";
        // DynamoDB returns null
        when(mapper.load(Member.class, nonexistentUsername)).thenReturn(null);

        // WHEN
        Member result = memberDao.getMember(nonexistentUsername);

        // THEN
        assertNull(result);
    }

    @Test
    void getMember_whenDynamoDbHasMemberWithUsername_returnsMember() {
        // GIVEN
        // existing username
        String existingUsername = "heyImAlreadyHere!";
        // expected member
        Member existingMember = new Member(existingUsername);
        // DynamoDB returns the member
        when(mapper.load(Member.class, existingUsername)).thenReturn(existingMember);

        // WHEN
        Member result = memberDao.getMember(existingUsername);

        // THEN
        assertEquals(existingMember, result);
    }

    @Test
    void getMember_withNull_throwsException() {
        // GIVEN
        // WHEN + THEN - null results in exception
        assertThrows(NullPointerException.class, () -> memberDao.getMember(null));
    }

    @Test
    void getMember_withEmptyString_throwsException() {
        // GIVEN
        // WHEN + THEN - "" results in exception
        assertThrows(IllegalArgumentException.class, () -> memberDao.getMember(""));
    }

    @Test
    void createMember_withMember_returnsMember() {
        // GIVEN
        // new member to save
        Member newMember = new Member("newmember");
        // DynamoDB saves successfully
        doNothing().when(mapper).save(newMember);

        // WHEN
        Member result = memberDao.createMember(newMember);

        // THEN
        assertEquals(newMember, result);
    }

    @Test
    void createMember_withNull_throwsException() {
        // GIVEN
        // WHEN + THEN - null member fails
        assertThrows(NullPointerException.class, () -> memberDao.createMember(null));
    }

    @Test
    void createMember_withNullField_throwsException() {
        // GIVEN
        // null username
        Member nullUsername = new Member(null);
        // null isActive
        Member nullActive = new Member("something");
        nullActive.setActive(null);
        // null karma points
        Member nullKarmaPoints = new Member("something else");
        nullKarmaPoints.setKarmaPointsAvailable(null);

        // WHEN + THEN - each null case fails
        assertThrows(IllegalArgumentException.class, () -> memberDao.createMember(nullUsername));
        assertThrows(IllegalArgumentException.class, () -> memberDao.createMember(nullActive));
        assertThrows(IllegalArgumentException.class, () -> memberDao.createMember(nullKarmaPoints));
    }
}
