package com.example.eventshuffle.models;

import java.util.List;

public record EventDetails(
        Long id,
        String name,
        List<String> dates,
        List<Vote> votes
) {}
