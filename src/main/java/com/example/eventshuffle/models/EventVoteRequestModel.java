package com.example.eventshuffle.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public record EventVoteRequestModel(
        @NotBlank(message = "Voter cannot be empty")
        String voter,
        @NotEmpty(message = "At least one date must be voted")
        List<String> votes) {
}
