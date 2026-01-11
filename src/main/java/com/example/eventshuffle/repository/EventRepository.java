package com.example.eventshuffle.repository;

import com.example.eventshuffle.helpers.EventDetailsMapper;
import com.example.eventshuffle.helpers.EventResultsMapper;
import com.example.eventshuffle.models.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.jooq.Tables.*;

@Repository
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

    public Long createNewEvent(CreateEventModel newEvent, DSLContext context) {

        return context.transactionResult(configuration -> {
            DSLContext dsl = DSL.using(configuration);

            Long eventId = dsl.insertInto(EVENT).set(EVENT.NAME, newEvent.name()).returning(EVENT.ID).fetchOne(EVENT.ID);

            if (newEvent.dates() != null && !newEvent.dates().isEmpty()) {

                var insert = dsl.insertInto(EVENTDATE, EVENTDATE.EVENTID, EVENTDATE.DATE);

                for (String date : newEvent.dates()) {
                    insert.values(eventId, LocalDate.parse(date));
                }
                insert.execute();

            }

            return eventId;
        });
    }

    public EventDetails getEventWithDetails(long id, DSLContext dsl) {

        var records = dsl.select(
                        EVENT.ID,
                        EVENT.NAME,
                        EVENTDATE.DATE,
                        VOTE.VOTER
                )
                .from(EVENT)
                .leftJoin(EVENTDATE).on(EVENTDATE.EVENTID.eq(EVENT.ID))
                .leftJoin(VOTE).on(VOTE.DATEID.eq(EVENTDATE.ID))
                .where(EVENT.ID.eq(id))
                .fetch();

        return EventDetailsMapper.map(records);
    }

    public EventDetails addVote(long eventId, EventVoteRequestModel voteRequest, DSLContext context) {

        return context.transactionResult(configuration -> {
            DSLContext transaction = DSL.using(configuration);

            var eventDateRecords =
                    transaction.select(EVENTDATE.ID, EVENTDATE.EVENTID, EVENTDATE.DATE, EVENTDATE.VOTES)
                            .from(EVENTDATE)
                            .where(EVENTDATE.EVENTID.eq(eventId))
                            .and(EVENTDATE.DATE.in(
                                    voteRequest.votes().stream()
                                            .map(LocalDate::parse)
                                            .toList()
                            ))
                            .fetch();

            for (var record : eventDateRecords) {
                Long dateId = record.get(EVENTDATE.ID);

                boolean alreadyVoted = transaction.fetchExists(
                        transaction.selectOne()
                                .from(VOTE)
                                .where(VOTE.DATEID.eq(dateId))
                                .and(VOTE.VOTER.eq(voteRequest.voter()))
                );
                if (!alreadyVoted) {
                    transaction.insertInto(VOTE)
                            .set(VOTE.DATEID, dateId)
                            .set(VOTE.VOTER, voteRequest.voter())
                            .execute();
                }
            }

            return getEventWithDetails(eventId, transaction);
        });
    }

    public EventResults getEventResults(long eventId, DSLContext context) {
        var records = context.select(
                        EVENT.ID,
                        EVENT.NAME,
                        EVENTDATE.DATE,
                        VOTE.VOTER
                )
                .from(EVENT)
                .leftJoin(EVENTDATE).on(EVENTDATE.EVENTID.eq(EVENT.ID))
                .leftJoin(VOTE).on(VOTE.DATEID.eq(EVENTDATE.ID))
                .where(EVENT.ID.eq(eventId))
                .fetch();

        return EventResultsMapper.map(records);
    }
}
