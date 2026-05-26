package com.kdongd.norunnolife.domain;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Workout {

    private final Long id;
    private final WorkoutType type;
    private final int durationMinutes;
    private final String memo;
    private final LocalDateTime workoutDateTime;

    private Workout(Long id, WorkoutType type, int durationMinutes, String memo, LocalDateTime workoutDateTime) {
        this.id = id;
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.memo = memo;
        this.workoutDateTime = workoutDateTime;
    }

    public static Workout create(WorkoutType type, int durationMinutes, String memo, LocalDateTime workoutDateTime) {
        return new Workout(null, type, durationMinutes, memo, workoutDateTime);
    }

    public static Workout withId(Long id, WorkoutType type, int durationMinutes, String memo, LocalDateTime workoutDateTime) {
        return new Workout(id, type, durationMinutes, memo, workoutDateTime);
    }

}