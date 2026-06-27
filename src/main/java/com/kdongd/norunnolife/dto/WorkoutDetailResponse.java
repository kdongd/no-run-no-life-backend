package com.kdongd.norunnolife.dto;

import com.kdongd.norunnolife.domain.WorkoutDetail;

public record WorkoutDetailResponse(
        Long id,
        Integer sequence,
        String label,
        Integer durationSeconds,
        String note
) {
    public static WorkoutDetailResponse from(WorkoutDetail detail) {
        return new WorkoutDetailResponse(
                detail.getId(),
                detail.getSequence(),
                detail.getLabel(),
                detail.getDurationSeconds(),
                detail.getNote()
        );
    }
}