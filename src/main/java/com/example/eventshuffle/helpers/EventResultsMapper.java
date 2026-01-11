package com.example.eventshuffle.helpers;

import com.example.eventshuffle.models.EventResults;
import com.example.eventshuffle.models.Vote;
import org.jooq.Record;
import org.jooq.Result;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.jooq.Tables.EVENT;
import static com.example.jooq.Tables.EVENTDATE;
import static com.example.jooq.Tables.VOTE;

public class EventResultsMapper {

    private EventResultsMapper() {}

    public static EventResults map(Result<? extends Record> records) {
        if (records.isEmpty()) {
            return null;
        }

        Long eventId = records.getFirst().get(EVENT.ID);
        String eventName = records.getFirst().get(EVENT.NAME);

        Set<String> participants = records.stream()
                .map(r -> r.get(VOTE.VOTER))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Map<String, List<String>> votesByDate = records.stream()
                .filter(record -> record.get(VOTE.VOTER) != null)
                .collect(Collectors.groupingBy(
                        r -> r.get(EVENTDATE.DATE).toString(),
                        Collectors.mapping(r -> r.get(VOTE.VOTER), Collectors.toList())
                ));

        List<Vote> suitableDates = votesByDate.entrySet().stream()
                .filter(dateVotes -> new HashSet<>(dateVotes.getValue()).containsAll(participants))
                .map(e -> new Vote(e.getKey(), e.getValue()))
                .toList();

        return new EventResults(eventId, eventName, suitableDates);
    }
}

