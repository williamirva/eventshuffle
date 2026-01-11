package com.example.eventshuffle.unit;

import com.example.eventshuffle.controller.EventController;
import com.example.eventshuffle.models.CreateEventModel;
import com.example.eventshuffle.models.EventVoteRequestModel;
import com.example.eventshuffle.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventController.class)
class EventControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    @Test
    void shouldReuturnBadRequestWhenEventNameIsEmpty() throws Exception {
        CreateEventModel request = new CreateEventModel("", List.of("2026-01-01"));
        ObjectMapper objectMapper = new ObjectMapper();

        this.mockMvc.perform(post("/api/v1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0]").value("name: Event name cannot be empty"));;
    }

    @Test
    void shouldReturnBadRequestWhenNoDates() throws Exception {
        EventVoteRequestModel request = new EventVoteRequestModel("Alice", List.of());
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/event/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0]").exists());


    }

    @Test
    void shouldReturnBadRequestWhenVoterIsEmpty() throws Exception {
        EventVoteRequestModel request = new EventVoteRequestModel("", List.of("2026-01-01"));
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/event/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0]").exists());
    }

    @Test
    void shouldReturnBadRequestWhenNoVotes() throws Exception {
        EventVoteRequestModel request = new EventVoteRequestModel("Dick", List.of());
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/api/v1/event/1/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errors[0]").exists());

    }
}
