package com.example.eventshuffle.models;

import java.util.List;

public record EventResults(
        Long id,
        String name,
        List<Vote> suitableDates
) {}
