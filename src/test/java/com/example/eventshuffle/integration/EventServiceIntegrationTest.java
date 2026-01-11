package com.example.eventshuffle.integration;

import com.example.eventshuffle.models.*;
import com.example.eventshuffle.service.EventService;
import org.jooq.DSLContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.jooq.tables.Event.EVENT;
import static com.example.jooq.tables.Eventdate.EVENTDATE;
import static com.example.jooq.tables.Vote.VOTE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class EventServiceIntegrationTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private DSLContext context;

    private Long testEventId;

    @Test
    void testCreateNewEventAndGetAll() {
        CreateEventModel newEvent = new CreateEventModel("Test Event", List.of("2026-01-15", "2026-01-16"));
        testEventId = eventService.createNewEvent(newEvent);
        assertNotNull(testEventId, "Event ID should not be null");

        List<Event> allEvents = eventService.getAllEvents();
        assertTrue(allEvents.stream().anyMatch(event -> event.id().equals(testEventId)),
                "Created event should appear in getAllEvents()");
    }

    @Test
    void testAddVote() {
        CreateEventModel newEvent = new CreateEventModel("Vote Test Event", List.of("2026-01-15", "2026-01-16"));
        testEventId = eventService.createNewEvent(newEvent);

        EventVoteRequestModel voteRequest = new EventVoteRequestModel("test-voter", List.of("2026-01-15"));
        EventDetails details = eventService.addVote(testEventId, voteRequest);

        assertNotNull(details, "EventDetails should not be null after voting");
        assertTrue(details.votes().stream()
                        .anyMatch(v -> v.voters().contains("test-voter")),
                "Vote should be recorded in EventDetails");
    }

    @AfterEach
    void cleanup() {
        if (testEventId != null) {
            context.deleteFrom(VOTE)
                    .where(VOTE.DATEID.in(context.select(EVENTDATE.ID).from(EVENTDATE)
                            .where(EVENTDATE.EVENTID.eq(testEventId)))).execute();

            context.deleteFrom(EVENTDATE).where(EVENTDATE.EVENTID.eq(testEventId)).execute();
            context.deleteFrom(EVENT).where(EVENT.ID.eq(testEventId)).execute();

            testEventId = null;
        }
    }
}
