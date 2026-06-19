package com.kdongd.norunnolife.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WorkoutDetailRequest(

        @NotNull(message = "순서는 필수입니다.")
        Integer sequence,

        String label,

        @Min(value = 1, message = "시간은 1초 이상이어야 합니다.")
        Integer durationSeconds,

        String note

) {}