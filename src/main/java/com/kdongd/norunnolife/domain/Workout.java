package com.kdongd.norunnolife.domain;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Workout {

    private Long id;
    private WorkoutType type;
    private int durationMinutes;
    private String memo;
    private LocalDateTime workoutDateTime;

    public Workout(WorkoutType type, int durationMinutes, String memo, LocalDateTime workoutDateTime) {
        this.type = type;
        this.durationMinutes = durationMinutes;
        this.memo = memo;
        this.workoutDateTime = workoutDateTime;
    }

    public void assignId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("이미 id가 존재합니다.");
        }
        this.id = id;
    }
}