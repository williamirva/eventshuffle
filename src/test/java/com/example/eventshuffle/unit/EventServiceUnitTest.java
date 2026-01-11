package com.example.eventshuffle.unit;

import com.example.eventshuffle.models.EventVoteRequestModel;
import com.example.eventshuffle.repository.EventRepository;
import com.example.eventshuffle.service.EventNotFoundException;
import com.example.eventshuffle.service.EventService;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceUnitTest {

    private static final long TEST_EVENT_ID = 99;

    @Mock
    private EventRepository eventRepository;
    private DSLContext context;

    @InjectMocks
    private EventService eventService;

    @Test
    void addVoteShouldThrowEventNotFoundExceptionWhenEventDoesNotExist() {
        EventVoteRequestModel requestModel = new EventVoteRequestModel("Harry", List.of("2026-01-01"));

        when(eventRepository.eventExists(TEST_EVENT_ID, context)).thenReturn(false);

        assertThrows(EventNotFoundException.class, () -> eventService.addVote(TEST_EVENT_ID, requestModel));

    }

    @Test
    void getEventResultsShouldThrowEventNotFoundExceptionWhenEventDoesNotExist() {
        assertThrows(EventNotFoundException.class, () ->
                eventService.getEventResults(TEST_EVENT_ID)
        );
    }
}
