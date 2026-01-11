package com.example.eventshuffle.repository;

import com.example.eventshuffle.controller.models.Event;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.jooq.Tables.EVENT;

public class EventRepository {

    public List<Event> getAllEvents(DSLContext context) {
        Result<Record> result = context.select().from(EVENT).fetch();

        return result.stream()
                .map(record -> new Event(
                        record.get(EVENT.ID),
                        record.get(EVENT.NAME)
                ))
                .collect(Collectors.toList());
    }
}
