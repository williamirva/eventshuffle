package com.example.eventshuffle.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public record CreateEventModel(
        @NotBlank(message = "Event name cannot be empty")
        String name,
        @NotEmpty(message = "At least one date is required")
        List<String> dates
) {}
