package com.example.eventshuffle.helpers;

import com.example.eventshuffle.models.EventDetails;
import com.example.eventshuffle.models.Vote;
import org.jooq.Record;
import org.jooq.Result;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.jooq.Tables.EVENT;
import static com.example.jooq.Tables.EVENTDATE;
import static com.example.jooq.Tables.VOTE;
public class EventDetailsMapper {

    private EventDetailsMapper() {
    }


    public static EventDetails map(Result<? extends Record> records) {
        if (records.isEmpty()) {
            return null;
        }

        Long eventId = records.getFirst().get(EVENT.ID);
        String eventName = records.getFirst().get(EVENT.NAME);

        List<String> dates = records.stream()
                .map(record -> record.get(EVENTDATE.DATE))
                .filter(Objects::nonNull)
                .map(Object::toString)
                .distinct()
                .collect(Collectors.toList());

        Map<String, List<String>> votesMap = records.stream()
                .filter(record -> record.get(VOTE.VOTER) != null)
                .collect(Collectors.groupingBy(
                        r -> r.get(EVENTDATE.DATE).toString(),
                        Collectors.mapping(r -> r.get(VOTE.VOTER), Collectors.toList())
                ));

        List<Vote> votes = votesMap.entrySet().stream()
                .map(dateVotes -> new Vote(dateVotes.getKey(), dateVotes.getValue()))
                .collect(Collectors.toList());

        return new EventDetails(eventId, eventName, dates, votes);
    }
}
