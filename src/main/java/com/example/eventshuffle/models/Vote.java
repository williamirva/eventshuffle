package com.example.eventshuffle.models;

import java.util.List;

public record Vote(
        String date,
        List<String> voters
) {}
