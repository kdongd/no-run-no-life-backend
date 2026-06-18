package com.kdongd.norunnolife.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private WorkoutType type;

    private Integer durationMinutes;
    private String memo;
    private LocalDateTime workoutDateTime;

    public static Workout create(WorkoutType type, Integer durationMinutes, String memo, LocalDateTime workoutDateTime) {
        Workout workout = new Workout();
        workout.type = type;
        workout.durationMinutes = durationMinutes;
        workout.memo = memo;
        workout.workoutDateTime = workoutDateTime;
        return workout;
    }

    public static Workout withId(Long id, WorkoutType type, Integer durationMinutes, String memo, LocalDateTime workoutDateTime) {
        Workout workout = new Workout();
        workout.id = id;
        workout.type = type;
        workout.durationMinutes = durationMinutes;
        workout.memo = memo;
        workout.workoutDateTime = workoutDateTime;
        return workout;
    }

    public void update(WorkoutType type, Integer durationMinutes, String memo, LocalDateTime workoutDateTime) {
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.memo = memo;
        this.workoutDateTime = workoutDateTime;
    }
}