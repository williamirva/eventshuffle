package com.example.eventshuffle.service;

import com.example.eventshuffle.models.*;
import com.example.eventshuffle.repository.EventRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final DSLContext context; // inject the DSLContext

    public EventService(EventRepository eventRepository, DSLContext context) {
        this.eventRepository = eventRepository;
        this.context = context;
    }

    public List<Event> getAllEvents() {
        return this.eventRepository.getAllEvents(this.context);
    }

    public Long createNewEvent(CreateEventModel newEvent) {
        return this.eventRepository.createNewEvent(newEvent, this.context);
    }

    public EventDetails getEventDetails(long id) {
        return this.eventRepository.getEventWithDetails(id, this.context);
    }

    public EventDetails addVote(long id, EventVoteRequestModel eventVoteRequestModel) {
        return this.eventRepository.addVote(id, eventVoteRequestModel, this.context);
    }

    public EventResults getEventResults(long id) {
        return this.eventRepository.getEventResults(id, this.context);
    }
}
