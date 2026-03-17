package com.maintain.vacation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maintain.vacation.dto.VacationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String USERNAME = "user";
    private final String PASSWORD = "user123";

    @Test
    void shouldReturn401WhenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetCurrentUser() throws Exception {
        mockMvc.perform(get("/api/users/me")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(USERNAME))
                .andExpect(jsonPath("$.remainingVacationDays").value(20));
    }

    @Test
    void shouldCreateVacation() throws Exception {
        VacationRequest request = new VacationRequest("2026-03-20", "2026-03-22");

        mockMvc.perform(post("/api/vacations")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.days").value(3))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldGetVacations() throws Exception {
        VacationRequest request = new VacationRequest("2026-03-20", "2026-03-22");

        mockMvc.perform(post("/api/vacations")
                .with(httpBasic(USERNAME, PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(get("/api/vacations")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].days").value(3));
    }

    @Test
    void shouldDeleteVacation() throws Exception {
        VacationRequest request = new VacationRequest("2026-03-20", "2026-03-22");

        String response = mockMvc.perform(post("/api/vacations")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(delete("/api/vacations/" + id)
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFailWhenInvalidDates() throws Exception {
        VacationRequest request = new VacationRequest("2026-03-25", "2026-03-20");

        mockMvc.perform(post("/api/vacations")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void shouldFailWhenNotEnoughDays() throws Exception {
        VacationRequest request = new VacationRequest("2026-03-01", "2026-04-30");

        mockMvc.perform(post("/api/vacations")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(org.hamcrest.Matchers.containsString("Not enough")));
    }
}