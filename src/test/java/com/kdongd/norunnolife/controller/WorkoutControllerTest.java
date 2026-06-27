package com.kdongd.norunnolife.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdongd.norunnolife.domain.WorkoutType;
import com.kdongd.norunnolife.dto.WorkoutRequest;
import com.kdongd.norunnolife.dto.WorkoutResponse;
import com.kdongd.norunnolife.exception.WorkoutNotFoundException;
import com.kdongd.norunnolife.service.WorkoutService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkoutController.class)
class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkoutService workoutService;

    private final LocalDateTime now = LocalDateTime.now().minusMinutes(1);

    @Test
    @DisplayName("GET /api/workouts - 200 반환")
    void getWorkouts_status200() throws Exception {
        given(workoutService.getWorkouts()).willReturn(List.of());

        mockMvc.perform(get("/api/workouts"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/workouts - 응답 JSON 배열 구조 검증")
    void getWorkouts_jsonArray() throws Exception {
        List<WorkoutResponse> responses = List.of(
                new WorkoutResponse(1L, WorkoutType.RUNNING, 30, "메모1", now, List.of()),
                new WorkoutResponse(2L, WorkoutType.BOXING, 60, "메모2", now, List.of())
        );
        given(workoutService.getWorkouts()).willReturn(responses);

        mockMvc.perform(get("/api/workouts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    @DisplayName("POST /api/workouts - 정상 요청 시 201 반환")
    void createWorkout_status201() throws Exception {
        WorkoutRequest request = new WorkoutRequest(WorkoutType.RUNNING, 30, "메모", now, null);
        WorkoutResponse response = new WorkoutResponse(1L, WorkoutType.RUNNING, 30, "메모", now, List.of());
        given(workoutService.createWorkout(any())).willReturn(response);

        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("POST /api/workouts - 응답 JSON 필드값 검증")
    void createWorkout_jsonFields() throws Exception {
        WorkoutRequest request = new WorkoutRequest(WorkoutType.RUNNING, 30, "메모", now, null);
        WorkoutResponse response = new WorkoutResponse(1L, WorkoutType.RUNNING, 30, "메모", now, List.of());
        given(workoutService.createWorkout(any())).willReturn(response);

        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("RUNNING"))
                .andExpect(jsonPath("$.durationMinutes").value(30))
                .andExpect(jsonPath("$.memo").value("메모"));
    }

    @Test
    @DisplayName("GET /api/workouts/{id} - 존재하는 id 조회 시 200 반환")
    void getWorkout_status200() throws Exception {
        WorkoutResponse response = new WorkoutResponse(1L, WorkoutType.RUNNING, 30, "메모", now, List.of());
        given(workoutService.getWorkout(1L)).willReturn(response);

        mockMvc.perform(get("/api/workouts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("GET /api/workouts/{id} - 존재하지 않는 id 조회 시 404 반환")
    void getWorkout_notFound() throws Exception {
        given(workoutService.getWorkout(999L)).willThrow(new WorkoutNotFoundException(999L));

        mockMvc.perform(get("/api/workouts/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/workouts - type null 시 400 반환")
    void createWorkout_typeNull() throws Exception {
        WorkoutRequest request = new WorkoutRequest(null, 30, "메모", now, null);

        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/workouts - durationMinutes 0 이하 시 400 반환")
    void createWorkout_durationInvalid() throws Exception {
        WorkoutRequest request = new WorkoutRequest(WorkoutType.RUNNING, 0, "메모", now, null);

        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/workouts - workoutDateTime null 시 400 반환")
    void createWorkout_dateTimeNull() throws Exception {
        WorkoutRequest request = new WorkoutRequest(WorkoutType.RUNNING, 30, "메모", null, null);

        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}