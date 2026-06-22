package com.kdongd.norunnolife.dto;

import com.kdongd.norunnolife.domain.WorkoutType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public record WorkoutRequest(

        @NotNull(message = "운동 종류는 필수입니다.")
        WorkoutType type,

        @NotNull(message = "운동 시간은 필수입니다.")
        @Min(value = 1, message = "운동 시간은 1분 이상이어야 합니다.")
        @Max(value = 600, message = "운동 시간은 600분 이하이어야 합니다.")
        Integer durationMinutes,

        @Size(max = 500, message = "메모는 500자 이하이어야 합니다.")
        String memo,

        @NotNull(message = "운동 날짜/시간은 필수입니다.")
        @PastOrPresent(message = "미래 날짜는 등록할 수 없습니다.")
        LocalDateTime workoutDateTime,

        @Valid
        List<WorkoutDetailRequest> details

) {}