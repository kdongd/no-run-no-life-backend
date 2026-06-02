package com.kdongd.norunnolife.dto;

import com.kdongd.norunnolife.domain.WorkoutType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record WorkoutRequest (

    @NotNull(message = "운동 종류는 필수입니다.")
    WorkoutType type,

    @Min(value = 1, message = "운동 시간은 1분 이상이어야 합니다.")
    int durationMinutes,

    String memo,

    @NotNull(message = "운동 날짜/시간은 필수입니다.")
    LocalDateTime workoutDateTime

) {}
