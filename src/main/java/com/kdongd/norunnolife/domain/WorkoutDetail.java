package com.kdongd.norunnolife.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkoutDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    private Workout workout;

    private Integer sequence;
    private String label;
    private Integer durationSeconds;
    private String note;

    public static WorkoutDetail create(Integer sequence, String label, Integer durationSeconds, String note) {
        WorkoutDetail detail = new WorkoutDetail();
        detail.sequence = sequence;
        detail.label = label;
        detail.durationSeconds = durationSeconds;
        detail.note = note;
        return detail;
    }

    void assignWorkout(Workout workout) {
        this.workout = workout;
    }
}