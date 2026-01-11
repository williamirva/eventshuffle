package com.example.eventshuffle.controller;

import com.example.eventshuffle.models.*;
import com.example.eventshuffle.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> events = this.eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PostMapping()
    public ResponseEntity<Long> createEvent(@Valid @RequestBody CreateEventModel newEvent) {
        Long newEventId = this.eventService.createNewEvent(newEvent);
        return ResponseEntity.ok(newEventId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetails> getEvent(@PathVariable long id){
        EventDetails eventDetails = this.eventService.getEventDetails(id);
        return ResponseEntity.ok(eventDetails);
    }

    @PostMapping("{id}/vote")
    public ResponseEntity<EventDetails> addVote(@PathVariable long id, @Valid @RequestBody EventVoteRequestModel eventVoteRequestModel) {
        EventDetails eventDetails = this.eventService.addVote(id, eventVoteRequestModel);
        return ResponseEntity.ok(eventDetails);
    }

    @PostMapping("/{id}/results")
    public ResponseEntity<EventResults> getEventResults(@PathVariable long id) {
        EventResults eventResults = this.eventService.getEventResults(id);
        return ResponseEntity.ok(eventResults);
    }

}
