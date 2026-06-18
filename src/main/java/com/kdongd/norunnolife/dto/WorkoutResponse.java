package com.kdongd.norunnolife.dto;

import com.kdongd.norunnolife.domain.Workout;
import com.kdongd.norunnolife.domain.WorkoutType;
import java.time.LocalDateTime;

public record WorkoutResponse(
        Long id,
        WorkoutType type,
        Integer durationMinutes,
        String memo,
        LocalDateTime workoutDateTime
) {
    public static WorkoutResponse from(Workout workout) {
        return new WorkoutResponse(
                workout.getId(),
                workout.getType(),
                workout.getDurationMinutes(),
                workout.getMemo(),
                workout.getWorkoutDateTime()
        );
    }
}