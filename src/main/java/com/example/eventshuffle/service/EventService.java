package com.example.eventshuffle.service;

import com.example.eventshuffle.controller.models.Event;
import com.example.eventshuffle.repository.EventRepository;
import org.jooq.DSLContext;

import java.util.List;

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
}
