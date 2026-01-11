package com.example.eventshuffle.service;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(long id) {
        super("Event with id " + id + " not found");
    }
}
